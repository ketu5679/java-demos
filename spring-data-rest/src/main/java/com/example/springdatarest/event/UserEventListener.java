package com.example.springdatarest.event;

import com.example.springdatarest.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserEventListener extends AbstractRepositoryEventListener<User> {

    @Override
    protected void onBeforeCreate(User entity) {
        log.info("onBeforeCreate user={}", entity);
    }

    @Override
    protected void onAfterCreate(User entity) {
        log.info("onAfterCreate user={}", entity);
    }

    @Override
    protected void onBeforeSave(User entity) {
        super.onBeforeSave(entity);
        log.info("onBeforeSave user={}", entity);
    }

    @Override
    protected void onAfterSave(User entity) {
        log.info("onAfterSave user={}", entity);
    }

    @Override
    protected void onBeforeDelete(User entity) {
        log.info("onBeforeDelete user={}", entity);
    }


    @Override
    protected void onAfterDelete(User entity) {
        log.info("onAfterDelete user={}", entity);
    }

    @Override
    protected void onBeforeLinkDelete(User parent, Object linked) {
        log.info("onBeforeLinkDelete user={}, linked={}", parent, linked);
    }

    @Override
    protected void onAfterLinkDelete(User parent, Object linked) {
        log.info("onAfterLinkDelete user={}, linked={}", parent, linked);
    }

    @Override
    protected void onBeforeLinkSave(User parent, Object linked) {
        log.info("onBeforeLinkSave user={}, linked={}", parent, linked);
    }

    @Override
    protected void onAfterLinkSave(User parent, Object linked) {
        log.info("onAfterLinkSave user={}, linked={}", parent, linked);
    }

}
