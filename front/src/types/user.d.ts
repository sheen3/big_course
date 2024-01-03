export interface ILoginInfo {
  username: string
  password: string
}

export interface IUserInfo {
  id: number
  username: string
  isAdmin: boolean
}

export interface IAuth {
  userInfo: IUserInfo
  token: stirng
}

export interface IUserDetailInfo {
  id: number,
  userName: string,
  employeeId: string,
  password: string,
  isAdmin: boolean,
  createdAt: Date,
  updatedAt: Date,
  deletedAt: Date,
  createdBy: number,
  updatedBy: number
}


export interface ICreateUser {
  userName: string,
  password: string,
}


export interface IEditUser {
  id: number,
  userName: string,
  password: string,
  isAdmin: boolean,
}
