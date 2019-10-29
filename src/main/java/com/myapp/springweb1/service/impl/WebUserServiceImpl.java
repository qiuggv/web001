package com.myapp.springweb1.service.impl;

import com.myapp.springweb1.Errors;
import com.myapp.springweb1.client.IUserClient;
import com.myapp.springweb1.dto.WebUser;
import com.myapp.springweb1.service.IWebUserService;
import com.github.pagehelper.PageInfo;
import com.myapp.springweb1.common.Response;
import com.myapp.springweb1.common.CodedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WebUserServiceImpl implements IWebUserService {
    private static Logger logger = LoggerFactory.getLogger(WebUserServiceImpl.class);

    @Autowired
    private IUserClient userClient;

    @Override
    public PageInfo<WebUser> query(String username, int pageNum, int pageSize) {
        Response<PageInfo<WebUser>> result = userClient.query(username, pageNum, pageSize);
            if (!result.isSuccess()) {
            throw new CodedException(result.getCode(), result.getMsg());
        }
        return result.getData();
    }

    @Override
    public WebUser selectById(Integer id) {
        logger.info("Select web user by id: [{}]", id);
        Response<WebUser> result = userClient.selectById(id);
        int code = result.getCode();
        // 实际应该根据不同业务异常做不同处理，这里只是简单的抛出异常
        if (code == Errors.USER_NOT_FOUND.getCode()) {
            throw Errors.USER_NOT_FOUND.exception();
        }
        if (code == Errors.USER_DISABLED.getCode()) {
            throw Errors.USER_DISABLED.exception();
        }
        if (code == Errors.USER_EXPIRED.getCode()) {
            throw Errors.USER_EXPIRED.exception();
        }
        return result.getData();
    }

    @Override
    public int save(WebUser user) {
        Response<Integer> result = userClient.save(user);
        if (!result.isSuccess()) {
            throw new CodedException(result.getCode(), result.getMsg());
        }
        return result.getData();
    }

    @Override
    public int update(WebUser user) {
        Response<Integer> result = userClient.update(user);
        if (!result.isSuccess()) {
            throw new CodedException(result.getCode(), result.getMsg());
        }
        return result.getData();
    }

    @Override
    public int deleteById(Integer id) {
        Response<Integer> result = userClient.deleteById(id);
        if (!result.isSuccess()) {
            throw new CodedException(result.getCode(), result.getMsg());
        }
        return result.getData();
    }

    @Override
    public WebUser selectUserById(Integer id) {
        WebUser webUser = userClient.selectUserById(id);
        return webUser;
    }
}
