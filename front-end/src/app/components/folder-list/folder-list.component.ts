import { User } from './../../model/user';
import { AuthenticationService } from './../../service/authentication.service';
import { CustomHttpResponse } from './../../model/custom-http-response';
import { NotificationType } from './../../enum/notification-type.enum';
import { ItemGroup } from './../../model/itemGroup';
import { NotificationService } from './../../service/notification.service';
import { TodoItemService } from './../../service/todo-item.service';
import { ItemGroupService } from './../../service/item-group.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-folder-list',
  templateUrl: './folder-list.component.html',
  styleUrls: ['./folder-list.component.css'],
})
export class FolderListComponent implements OnInit {
  public itemGroups: ItemGroup[];
  public editItemGroup: ItemGroup;
  public currentDesc: string;
  public user: User;
  public isLoggedIn: boolean;
  constructor(
    private itemGroupService: ItemGroupService,
    private todoItemService: TodoItemService,
    private router: Router,
    private authenticationService: AuthenticationService,
    private notificationService: NotificationService
  ) {}

  ngOnInit() {
    this.getItemGroups(true);
    this.editItemGroup = new ItemGroup();
    this.isLoggedIn = this.authenticationService.isUserLoggedIn();
    this.user = this.authenticationService.getUserFromLocalCache();
  }

  public onLogOut(): void {
    this.authenticationService.logOut();
    this.router.navigate(['/login']);
    window.location.reload();

  }

  public saveNewItemGroup(): void {
    this.clickButton('new-itemGroup-save');
  }

  public getItemGroups(showNotification: boolean): void {
    this.itemGroupService.listAll().subscribe(
      (response: ItemGroup[]) => {
        this.itemGroups = response;
        if (showNotification) {
          this.sendNotification(
            NotificationType.SUCCESS,
            `${response.length} folder(s) loaded successfully.`
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

  public onUpdateItemGroup(): void {
    const formData = this.itemGroupService.createItemGroupSaveFormData(
      this.editItemGroup.idItemGroup.toString(),
      this.editItemGroup
    );
    this.itemGroupService.save(formData).subscribe(
      (response: ItemGroup) => {
        this.clickButton('closeEditItemGroupModalButton');
        this.getItemGroups(false);
        this.sendNotification(
          NotificationType.SUCCESS,
          `Task updated successfully.`
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

  public onAddNewItemGroup(itemGroupForm: NgForm): void {
    console.log(itemGroupForm.value);
    const formData = this.itemGroupService.createItemGroupSaveFormData(
      null,
      itemGroupForm.value
    );
    this.itemGroupService.save(formData).subscribe(
      (response: ItemGroup) => {
        this.clickButton('new-itemGroup-close');
        this.getItemGroups(false);
        itemGroupForm.reset();
        this.sendNotification(
          NotificationType.SUCCESS,
          `New folder added succesfully.`
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

  public onEditItemGroup(editItemGroup: ItemGroup): void {
    this.editItemGroup = editItemGroup;
    this.currentDesc = editItemGroup.descItemGroup;
    this.clickButton('openItemGroupEdit');
  }

  public onDeleteItemGroup(idItemGroup: bigint): void {
    if (
      confirm(
        '¿Are you sure do you want to delete this folder?'
      )
    ) {
      this.itemGroupService.delete(idItemGroup).subscribe(
        (response: CustomHttpResponse) => {
          this.sendNotification(NotificationType.SUCCESS, response.message);
          this.getItemGroups(false);
        },
        (error: HttpErrorResponse) => {
          this.sendNotification(NotificationType.ERROR, error.error.message);
        }
      );
    }
  }

  public onManageTodoItems(idItemGroup: bigint): void {
    this.router.navigate(['/todoItems/management'], {
      queryParams: { idItemGroup: idItemGroup },
    });
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
