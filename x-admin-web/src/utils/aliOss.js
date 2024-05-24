// aliOss.js

import OSS from 'ali-oss';

let ossClient = null;

export default {
  install(Vue, options) {
    const ossConfig = {
      accessKeyId:
      accessKeySecret:
      bucket:
      region:
      secure: true,
      // 更多配置项可以根据需要添加
    };

    ossClient = new OSS(ossConfig);

    Vue.prototype.$oss = ossClient;
  }
}


// http://boluo12138.oss-cn-beijing.aliyuncs.com/1691570713867_3.jpg
// const fileName = Date.now() + "_" + file.name;
// 调用OSS SDK的put方法上传文件
// client
//   .put(file.name, file)
//   .then((response) => {
//     console.log("图片上传成功:", response);
//     // 在这里可以处理上传成功的逻辑，比如保存返回的文件URL等
//     if (response.res.status === 200) {
//       // console.log(response.url)
//       this.imageUrl = response.url;
//       this.userForm.avatar = this.imageUrl;
//       // store.commit("user/SET_AVATAR", this.imageUrl);
//       console.log(
//         "store avatar: ",
//         store.state.user.avatar,
//         this.userForm.avatar
//       );
//     }
//   })
//   .catch((error) => {
//     console.error("图片上传失败:", error);
//   });
