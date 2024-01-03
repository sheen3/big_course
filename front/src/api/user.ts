import instance from '@/api/base'
import {IAuth, ICreateUser, IEditUser, ILoginInfo, IUserDetailInfo} from "@/types/user";


export const auth = ({ username, password }: ILoginInfo) => {
  return instance.post<IAuth, IAuth>('/user/login', {
    username: username,
    password: password
  })
}


export const fetchUserListData = () => {
  return instance.get<IUserDetailInfo[],IUserDetailInfo[]>('user/getAllUser')
}


export const fetchCreateUser = (data: ICreateUser) => {
  return instance.post(
      'user/createUser',
      data
  )
}

export const fetchEditUser = (data: IEditUser) => {
  return instance.post(
      'user/editUser',
      data
  )
}
