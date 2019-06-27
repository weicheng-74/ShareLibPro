package com.twc.mylib;

import android.app.Activity;
import android.graphics.Bitmap;


import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.File;

/**
 * 分享工具类 适合友盟配置
 */
public class ShareUtils {
    public static ShareUtils shareUtils;
    private static Activity activity;
    private String shareUrl;

    public ShareUtils(Activity activity) {
        this.activity = activity;
    }

    public static ShareUtils newInstance(Activity activity) {
        if (shareUtils == null) {
            shareUtils = new ShareUtils(activity);
        }
        return shareUtils;
    }

    private UMImage umImage = null;
    private String title = "";

    public ShareUtils setImage(UMImage umImage) {
        this.umImage = umImage;
        return this;
    }

    public ShareUtils setTitle(String title) {
        this.title = title;
        return this;
    }

    public ShareUtils setUrl(String shareUrl) {
        this.shareUrl = shareUrl;
        return this;
    }

    /**
     * 分享链接
     *
     * @param umShareListener
     * @return
     */
    public ShareUtils shareUrl(UMShareListener umShareListener) {
        if (activity == null) {
            return this;
        }
        UMWeb web = new UMWeb(shareUrl);
        web.setTitle(title);//标题
        web.setThumb(getCompress(umImage));  //缩略图
        web.setDescription("");//描述
        new ShareAction(activity)
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(umShareListener)
                .open();
        return this;
    }


    public ShareUtils share(UMShareListener umShareListener) {
        if (activity == null) {
            return this;
        }
        new ShareAction(activity)
                .withText(title).withMedia(umImage)
                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(umShareListener)
                .open();
        return this;
    }

    public UMImage getCompress(UMImage umImage) {
        UMImage image = umImage;
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        //image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
        //压缩格式设置
        image.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色
        return image;
    }

    /**
     * 分享本地图片
     *
     * @param drawableId
     * @return
     */
    public static UMImage getUMImage(int drawableId) {
        return new UMImage(activity, drawableId);
    }


    /**
     * 分享本地文件
     *
     * @param file
     * @return
     */
    public static UMImage getUMImage(File file) {
        return new UMImage(activity, file);
    }


    /**
     * 分享网络图片
     *
     * @param url
     * @return
     */
    public static UMImage getUMImage(String url) {
        return new UMImage(activity, url);
    }


}
