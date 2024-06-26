import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

export function register(data) {
  return request({
    url: '/user/register',
    method: 'post',
    data
  })
}

export function sendEmail(data){
  return request({
    url:'/user/sendEmail',
    method:'post',
    data
  })
}

export function getUserInfo(token) {
  return request({
    url: '/user/userInfo',
    method: 'get',
    params: { token }
  })
}



export default {
  selectById(id){
    return request({
      url: `/user/${id}`,
      method: 'get',
    })
  },
   resetPassword(data){
    return request({
      url:'/user/resetPassword',
      method:'post',
      data
    })
  }
}
