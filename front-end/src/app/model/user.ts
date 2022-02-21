export class User {
    public id: bigint;
    public userId: string;
    public name: string;
    public username: string;
    public password: string;
    public email: string;
    public active: boolean;
    public notLocked: boolean;
    public role: string;
    public authorities: [];

    constructor() {
      this.userId = '';
      this.name = '';
      this.username = '';
      this.email = '';
      this.active = false;
      this.notLocked = false;
      this.role = '';
      this.authorities = [];
    }

  }
