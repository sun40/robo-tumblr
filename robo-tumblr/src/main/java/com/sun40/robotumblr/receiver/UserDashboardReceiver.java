package com.sun40.robotumblr.receiver;

import android.os.Bundle;
import android.os.Handler;

import com.sun40.robotumblr.OnTokenInvalidatedListener;
import com.sun40.robotumblr.TumblrService;
import com.sun40.robotumblr.model.Post;

import java.util.List;

/**
 * Created by Alexander Sokol
 * on 14.09.15 13:22.
 */
public class UserDashboardReceiver extends BaseResultReceiver<UserDashboardReceiver.UserDashboardListener> {


    public UserDashboardReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onStart(Bundle data) {

    }

    @Override
    protected void onProgress(Bundle data) {

    }

    @Override
    protected void onError(Bundle data) {
        getListener().onUserDashboardFail(data.isEmpty() ? null : data.getString(TumblrService.KEY_ERROR));
    }

    @Override
    protected void onFinish(Bundle data) {
        List<Post> posts = data.getParcelableArrayList(TumblrService.KEY_POSTS);
        if (posts != null) {
            int limit = data.getInt(TumblrService.KEY_LIMIT);
            int offset = data.getInt(TumblrService.KEY_OFFSET);
            String type = data.getString(TumblrService.KEY_TYPE);
            long sinceId = data.getLong(TumblrService.KEY_ID);
            boolean reblogInfo = data.getBoolean(TumblrService.KEY_REBLOG_INFO);
            boolean notesInfo = data.getBoolean(TumblrService.KEY_NOTES_INFO);
            getListener().onUserDashboardSuccess(posts, limit, offset, type, sinceId, reblogInfo, notesInfo);
        } else
            getListener().onUserDashboardFail(null);

    }

    public interface UserDashboardListener extends OnTokenInvalidatedListener {
        void onUserDashboardFail(String error);

        void onUserDashboardSuccess(List<Post> posts, int limit, int offset, String type, long sinceId, boolean reblogInfo, boolean notesInfo);
    }
}
