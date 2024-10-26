import request from '@/utils/request.js'

//Provide function to call register interface
export const userRegisterService = (data) => {
    const params = new URLSearchParams()
    for (let key in data) {
      params.append(key, data[key])
    }
    return request.post('/user/register', params)
  }

//Provide function to call login interface
export const userLoginService = (data) => {
    const params = new URLSearchParams()
    for (let key in data) {
      params.append(key, data[key])
    }
    return request.post('/user/login', params)
}

export const userInfoGetService = ()=>{
  return request.get('/user/userInfo');
}

export const userInfoUpdateService = (userInfo)=>{
  return request.put('/user/update',userInfo)
}

export const userAvatarUpdateService=(avatarUrl)=>{
  let params = new URLSearchParams();
  params.append('avatarUrl',avatarUrl)
  console.log('params:', params);
  return request.patch('/user/updateAvatar',params)
}


