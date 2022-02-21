import { ItemGroupService } from './../../service/item-group.service';
import { CustomHttpResponse } from './../../model/custom-http-response';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { NotificationType } from './../../enum/notification-type.enum';
import { NotificationService } from './../../service/notification.service';
import { TodoItemService } from './../../service/todo-item.service';
import { ItemGroup } from './../../model/itemGroup';
import { TodoItem } from './../../model/todoItem';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css'],
})
export class TaskListComponent implements OnInit {
  public todoItems: TodoItem[];
  public editTodoItem: TodoItem;
  public itemGroup: ItemGroup;
  public currentDescTodoItem: string;
  public idItemGroup: any;
  public currentFolderName: any;

  constructor(
    private todoItemService: TodoItemService,
    private itemGroupService: ItemGroupService,
    private route: ActivatedRoute,
    private router: Router,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.idItemGroup = params['idItemGroup'];
    });
    this.getTodoItems(this.idItemGroup, true);
    this.editTodoItem = new TodoItem();
    this.getCurrentFolderName(this.idItemGroup);
  }

  public getCurrentFolderName(idItemGroup: string): void {
    this.itemGroupService.findById(idItemGroup).subscribe(
      (response: ItemGroup) => {
        this.currentFolderName = response.descItemGroup;
      },
      (errorResponse: HttpErrorResponse) => {
        this.sendNotification(
          NotificationType.ERROR,
          errorResponse.error.message
        );
      }
    );
  }

  public getTodoItems(idItemGroup: string, showNotification: boolean): void {
    this.todoItemService.listAllByItemGroup(idItemGroup).subscribe(
      (response: TodoItem[]) => {
        this.todoItems = response;
        if (response != undefined) {
          if (showNotification) {
            this.sendNotification(
              NotificationType.SUCCESS,
              `${response.length} task(s) loaded successfully.`
            );
          }
        }else{
          this.sendNotification(
            NotificationType.WARNING,
            `This folder has no tasks yet.`
          );
        }
      },
      (errorResponse: HttpErrorResponse) => {
        this.sendNotification(
          NotificationType.ERROR,
          errorResponse.error.message
        );
      }
    );
  }

  public saveNewTodoItem(): void {
    this.clickButton('new-todoItem-save');
  }

  public onAddNewTodoItem(todoItemForm: NgForm): void {
    const formData = this.todoItemService.createTodoItemSaveFormData(
      this.idItemGroup,
      null,
      todoItemForm.value
    );
    this.todoItemService.save(formData).subscribe(
      (response: TodoItem) => {
        this.clickButton('new-todoItem-close');
        this.getTodoItems(this.idItemGroup, false);
        todoItemForm.reset();
        this.sendNotification(
          NotificationType.SUCCESS,
          `Task saved successfully.`
        );
      },
      (errorResponse: HttpErrorResponse) => {
        this.sendNotification(
          NotificationType.ERROR,
          errorResponse.error.message
        );
      }
    );
  }

  public onUpdateTodoItem(showNotification: boolean): void {
    const formData = this.todoItemService.createTodoItemSaveFormData(
      this.idItemGroup,
      this.editTodoItem.idTodoItem.toString(),
      this.editTodoItem
    );
    this.todoItemService.save(formData).subscribe(
      (response: TodoItem) => {
        this.clickButton('closeEditTodoItemModalButton');
        this.getTodoItems(this.idItemGroup, false);

        if (showNotification) {
          this.sendNotification(
            NotificationType.SUCCESS,
            `Task updated successfully.`
          );
        }
      },
      (errorResponse: HttpErrorResponse) => {
        this.sendNotification(
          NotificationType.ERROR,
          errorResponse.error.message
        );
      }
    );
  }

  public onRegresar(): void {
    this.router.navigate(['/index']);
  }

  public onEditTodoItem(editTodoItem: TodoItem): void {
    this.editTodoItem = editTodoItem;
    this.currentDescTodoItem = editTodoItem.descTodoItem;
    this.clickButton('openTodoItemEdit');
  }

  public changeStatus(idTodoItem: bigint) {
    this.todoItemService.findById(idTodoItem.toString()).subscribe(
      (response: TodoItem) => {
        this.editTodoItem = response;
        if (!this.editTodoItem.isDone) {
          this.sendNotification(
            NotificationType.INFO,
            `Task marked as completed.`
          );
          this.editTodoItem.isDone = true;
        } else {
          this.sendNotification(
            NotificationType.WARNING,
            `Task marked as uncompleted.`
          );
          this.editTodoItem.isDone = false;
        }
        this.onUpdateTodoItem(false);
      },
      (errorResponse: HttpErrorResponse) => {
        this.sendNotification(
          NotificationType.ERROR,
          errorResponse.error.message
        );
      }
    );
  }

  public onDeleteTodoItem(idTodoItem: bigint): void {
    if (confirm('¿Are you sure do you want to delete this task?')) {
      this.todoItemService.delete(idTodoItem).subscribe(
        (response: CustomHttpResponse) => {
          this.sendNotification(NotificationType.SUCCESS, response.message);
          this.getTodoItems(this.idItemGroup, false);
        },
        (error: HttpErrorResponse) => {
          this.sendNotification(NotificationType.ERROR, error.error.message);
        }
      );
    }
  }

  private sendNotification(
    notificationType: NotificationType,
    message: string
  ): void {
    if (message) {
      this.notificationService.notify(notificationType, message);
    } else {
      this.notificationService.notify(
        notificationType,
        'Ocurrió un error. Inténtalo de nuevo.'
      );
    }
  }

  private clickButton(buttonId: string): void {
    document.getElementById(buttonId).click();
  }
}
