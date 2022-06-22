package com.myself.seckill.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myself.seckill.entity.User;
import com.myself.seckill.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2021-01-19
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;
    // 悲观锁 pessimisticLock 库存
    @RequestMapping("/userList")
    public R pessimisticLock(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize")int pageSize,@RequestParam(value = "userName",required = false) String userName) throws Exception {
       Page<User>  user=userService.queryPage(pageNo,pageSize,userName);
        return R.ok(user);
    }
}
