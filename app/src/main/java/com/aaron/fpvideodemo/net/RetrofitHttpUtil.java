package com.aaron.fpvideodemo.net;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;


public class RetrofitHttpUtil {

    private static RetrofitHttpUtil netService = new RetrofitHttpUtil();

    public static RetrofitHttpUtil getInstance() {
        return netService;
    }

    private RetrofitHttpUtil() {
    }

    private ApiService service;

    public ApiService getService() {

        if (service == null) {

            //OkHttp配置
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//log日志
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .addInterceptor(interceptor)
                    .retryOnConnectionFailure(true)//错误重连
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
//                    .addNetworkInterceptor()//网络拦截器
                    .build();

            //Retrofit配置
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiService.APP_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .build();

            service = retrofit.create(ApiService.class);

        }

        return service;
    }

//    public ApiService getAddressService() {
//
////        if (service == null) {
//
//            //OkHttp配置
//            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//log日志
//            OkHttpClient client = new OkHttpClient().newBuilder()
//                    .addInterceptor(interceptor)
//                    .retryOnConnectionFailure(true)//错误重连
//                    .connectTimeout(10, TimeUnit.SECONDS)
//                    .readTimeout(10, TimeUnit.SECONDS)
////                    .addNetworkInterceptor()//网络拦截器
//                    .build();
//
//            //Retrofit配置
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(ApiService.WEB_ROOT_ADDRESS)
//                    .client(client)
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .addConverterFactory(FastJsonConverterFactory.create())
//                    .build();
//
//            service = retrofit.create(ApiService.class);
//
////        }
//
//        return service;
//    }


//    /**
//     * 根据城市名称获取经纬度
//     *
//     * @param
//     */
//    public void recodeAddress(Observer subscriber, String address, String output, String key, String city) {
//        getAddressService().recodeAddress(address, output, key, city)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }


    /**
     * 转换器
     *
     * @return
     */
    private ObservableTransformer schedulersTransformer() {

        return new ObservableTransformer() {

            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };

    }


    /**
     * 我的作品
     */
    public void myUgcList(Observer subscriber, String userId, int page, int size, String keyWord) {
        getService().myUgcList(userId, page, size, keyWord)
                .compose(schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 短视频点赞
     */
    public void vodLike(Observer subscriber, String userId, int type, String fileId) {
        getService().vodLike(userId, type, fileId)
                .compose(schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 我点赞的列表
     */
    public void myLikeList(Observer subscriber, String userId, int page, int size, String keyWord) {
        getService().myLikeList(userId, page, size, keyWord)
                .compose(schedulersTransformer())
                .subscribe(subscriber);
    }


    /**
     * 关注用户
     *
     * @param userId       关注用户id
     * @param likeUserId   被关注用户id
     * @param likeUserImg  被关注用户img
     * @param likeUserName 被关注用户name
     */
    public void userLike(Observer subscriber, int type, String userId, String likeUserId, String likeUserImg, String likeUserName) {
        getService().userLike(type, userId, likeUserId, likeUserImg, likeUserName)
                .compose(schedulersTransformer())
                .subscribe(subscriber);
    }


    /**
     * 增加浏览次数
     *
     * @param times  浏览次数
     * @param fileId 点播文件id
     */
    public void vodView(Observer subscriber, int times, String fileId) {
        getService().vodView(times, fileId)
                .compose(schedulersTransformer())
                .subscribe(subscriber);
    }


    /**
     * user和vod是否是我关注和点赞过
     *
     * @param userId     用户id
     * @param likeFileId 查询视频Id
     * @param likeUserId 查询该视频的作者Id
     */
    public void isLikeUserVod(Observer subscriber, String userId, String likeFileId, String likeUserId) {
        getService().isLikeUserVod(userId, likeFileId, likeUserId)
                .compose(schedulersTransformer())
                .subscribe(subscriber);
    }


    /**
     * 短视频列表
     *
     * @param type    用户id 视频列表种类,1:推荐,2:热门
     * @param keyWord
     */
    public void videoList(Observer subscriber, int type, int page, int size, String keyWord) {
        getService().videoList(type, page, size, keyWord)
                .compose(schedulersTransformer())
                .subscribe(subscriber);
    }


    /**
     * 我关注的列表
     */
    public void myLikeUserList(Observer subscriber, String userId, int page, int size, String keyWord) {
        getService().myLikeUserList(userId, page, size, keyWord)
                .compose(schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 粉丝数和关注数
     */
    public void fansAndLikeNum(Observer subscriber, String userId) {
        getService().fansAndLikeNum(userId)
                .compose(schedulersTransformer())
                .subscribe(subscriber);
    }


    //    /**
    //     * 获取付费医生列表
    //     *
    //     * @param ch         登录通道(0 电脑，1安卓，2苹果)
    //     * @param hosptialid 医院id
    //     * @param searchname 搜索名称
    //     * @param departid   科室id
    //     * @param doctitleid 医生等级id
    //     * @param hosgradid  医院等级id
    //     * @param asktype    问诊方式 1-图文 2-电话 3-视频
    //     * @param sorttype   排序方式
    //     * @param page       分页参数
    //     * @param size       分页参数
    //     */
//    public void getDoctorList(Observer subscriber, String ch, String hosptialid, String searchname, String departid, String doctitleid
//            , String hosgradid, String asktype, String sorttype, int page, String size) {
//
//        getService().getPayDoctorList(ch, hosptialid, searchname, departid, doctitleid, hosgradid, asktype, sorttype, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
////        switch (chargeFree) {
////
////            case SpValue.ASK_CHARGE:
////
////                break;
////
////            case SpValue.ASK_FREE:
////
////                getService().getFreeDoctorList(ch, hosptialid, diseaseid, departid, doctitleid, hosgradid, asktype, sorttype, page, size)
////                        .compose(schedulersTransformer())
////                        .subscribe(subscriber);
////                break;
////        }
//
//    }
//
//    /**
//     * 获取免费医生列表
//     *
//     * @param ch
//     */
//    public void getFreeDoctorList(Observer subscriber, String ch, String hosptialid, String departid,
//                                  String doctitleid, String hosgradid, String docname, int page, String size) {
//        getService().getFreeDoctorList(ch, hosptialid, departid, doctitleid, hosgradid, docname, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//    /**
//     * 获取医院等级列表
//     *
//     * @param ch
//     */
//    public void getHosGradeLevelList(Observer subscriber, String ch) {
//        getService().getHosGradeLevelList(ch)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//
//    /**
//     * 获取医生等级列表
//     *
//     * @param ch
//     */
//    public void getDoctorLevelList(Observer subscriber, String ch) {
//        getService().getDoctorLevelList(ch)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//
//    /**
//     * 获取全部科室
//     *
//     * @param ch
//     */
//    public void getAllDepartment(Observer subscriber, String ch) {
//        getService().getAllDepartment(ch)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//    /**
//     * 获取常用疾病
//     *
//     * @param ch
//     */
//    public void getCommentDisList(Observer subscriber, String ch) {
//        getService().getCommentDisList(ch)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//    /**
//     * 获取常用疾病
//     *
//     * @param ch
//     */
//    public void getBannerList(Observer subscriber, String ch, String pagemark, String hospitalid) {
//        getService().getBanner(ch, pagemark, hospitalid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//    /**
//     * 获取常用疾病
//     *
//     * @param ch
//     */
//    public void getHospital(Observer subscriber, String ch, String longitude, String latitude) {
//        getService().getHospital(ch, longitude, latitude)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//    /**
//     * 获取首页 综合推荐
//     */
//    public void getRecommand(Observer subscriber, String ch, String hospitalid, int page, String page_size, String token) {
//
//        getService().getRecommand(ch, hospitalid, page, page_size, token)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 获取首页 综合推荐
//     */
//    public void myFamilyList(Observer subscriber, String token, String ch) {
//
//        getService().myFamilyList(token, ch)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 添加家庭成员
//     */
//    public void addFamilyNum(Observer subscriber, String token, String ch, String name, String sex, String birthdate, String relationship, String pcode
//            , String ccode, String acode, String phone, String IDcard, String medicalcard, String diseasehistory, String anaphylaxis, String operation
//            , String imgurl, String province, String city, String area) {
//
//        getService().addFamilyNum(token, ch, name, sex, birthdate, relationship, pcode, ccode, acode, phone, IDcard, medicalcard
//                , diseasehistory, anaphylaxis, operation, imgurl, province, city, area)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 修改家庭成员信息
//     */
//    public void modifyFamilyNum(Observer subscriber, String token, String ch, String id, String name, String sex, String birthdate, String relationship, String pcode
//            , String ccode, String acode, String phone, String IDcard, String medicalcard, String diseasehistory, String anaphylaxis, String operation
//            , String imgurl, String province, String city, String area) {
//
//        getService().modifyFamilyNum(token, ch, id, name, sex, birthdate, relationship, pcode, ccode, acode, phone, IDcard, medicalcard
//                , diseasehistory, anaphylaxis, operation, imgurl, province, city, area)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 省份列表
//     */
//    public void provinceList(Observer subscriber, String ch) {
//        getService().provinceList(ch)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 城市列表
//     */
//    public void cityList(Observer subscriber, String ch, String proviceCode) {
//        getService().cityList(ch, proviceCode)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 区列表
//     */
//    public void areaList(Observer subscriber, String ch, String cityCode) {
//        getService().areaList(ch, cityCode)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 问诊列表
//     */
//    public void askList(Observer subscriber, String token, String ch, String type
//            , int page, String size) {
//        getService().askList(token, ch, type, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 取消问诊订单
//     */
//    public void cancleInquiryOrder(Observer subscriber, String token, String ch, String orderid
//            , String status, String reason) {
//        getService().cancleInquiryOrder(token, ch, orderid, status, reason)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 删除问诊订单
//     */
//    public void deleteInquiryOrder(Observer subscriber, String token, String ch, String orderid) {
//        getService().deleteInquiryOrder(token, ch, orderid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 我的问诊订单列表
//     */
//    public void myInquiry(Observer subscriber, String token, String ch, String type
//            , String status, int page, String size) {
//        getService().myInquiry(token, ch, type, status, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 删除家庭成员
//     */
//    public void deleteFamilyNum(Observer subscriber, String token, String ch, String id) {
//        getService().deleteFamilyNum(token, ch, id)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//    /**
//     * 第三方登录
//     */
//    public void thirdLogin(Observer subscriber, String openid, String dtype, String ch) {
//        getService().thirdLogin(openid, dtype, ch)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//    /**
//     * 绑定第三方登录
//     */
//    public void bindThirdLogin(Observer subscriber, String openid, String dtype, String token) {
//        getService().bindThirdLogin(openid, dtype, token)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//    /**
//     * 退出登录
//     */
//    public void exitApp(Observer subscriber, String token, String ch) {
//        getService().exitApp(token, ch)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//
//    /**
//     * 获取全部疾病
//     */
//    public void allDis(Observer subscriber, String ch) {
//        getService().allDisease(ch)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
////    /**
////     * 上传图片
////     */
////    public void uploadImages(Observer subscriber, String ch, String type, MultipartBody.Part parts) {
////        getService().uploadPic(ch, type, parts)
////                .compose(schedulersTransformer())
////                .subscribe(subscriber);
////    }
//
//    /**
//     * 上传图片
//     */
//    public void uploadImages(Observer subscriber, String ch, Integer type, List<MultipartBody.Part> parts) {
//        getService().uploadPic(ch, type, parts)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//    /**
//     * 上传录音文件
//     */
//    public void uploadVoice(Observer subscriber, String ch, Integer type, MultipartBody.Part parts) {
//        getService().uploadVoice(ch, type, parts)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//    /**
//     * 创建付费问诊
//     */
//    public void createPayAsk(Observer subscriber, String token, String ch, String flokid
//            , String diseaseid
//            , String hospitalid, String problem, String describe
//            , String imgs, String voiceurl, String doctorid
//            , String asktype, String asktime, String price) {
//        getService().createPayAsk(token, ch, flokid, diseaseid, hospitalid, problem, describe, imgs, voiceurl, doctorid, asktype, asktime, price)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 创建免费问诊
//     */
//    public void createFreeAsk(Observer subscriber, String token, String ch, String flokid
//            , String diseaseid
//            , String hospitalid, String problem, String describe
//            , String imgs, String voiceurl, String doctorid) {
//        getService().createFreeAsk(token, ch, flokid, diseaseid, hospitalid, problem, describe, imgs, voiceurl, doctorid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 问诊 微信支付信息
//     */
//    public void wxPayInfo(Observer subscriber, String token, String ch, String groupnum) {
//        getService().wxPayInfo(token, ch, groupnum)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 商品 微信支付信息
//     */
//    public void wxGoodsPayInfo(Observer subscriber, String token, String ch, String groupnum) {
//        getService().wxGoodsPayInfo(token, ch, groupnum)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//
//    /**
//     * splish页面
//     */
//    public void getSplishPage(Observer subscriber, String token, String ch) {
//        getService().getSplishPage(token, ch)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 医生详情
//     */
//    public void doctorDetail(Observer subscriber, String token, String ch, String doctorid) {
//        getService().doctorDetail(token, ch, doctorid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 医生详情的评价列表
//     */
//    public void doctorDetailCommentList(Observer subscriber, String token, String ch, String doctorid, int page, String size) {
//        getService().doctorDetailCommentList(token, ch, doctorid, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 医生详情的 问诊记录
//     */
//    public void inquiryHistory(Observer subscriber, String ch, String doctorid, int page, String size) {
//        getService().inquiryHistory(ch, doctorid, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 医生详情的 医讲堂记录
//     */
//    public void videoHistory(Observer subscriber, String ch, String doctorid, int page, String size) {
//        getService().videoHistory(ch, doctorid, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 医生服务项目
//     */
//    public void serviceInfo(Observer subscriber, String ch, String doctorid) {
//        getService().serviceInfo(ch, doctorid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 支付宝支付信息
//     */
//    public void aliPayInfo(Observer subscriber, String token, String ch, String groupnum) {
//        getService().aliPayInfo(token, ch, groupnum)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 支付宝商品支付信息
//     */
//    public void aliPayGoodsInfo(Observer subscriber, String token, String ch, String groupnum) {
//        getService().aliPayGoodsInfo(token, ch, groupnum)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 获取运费信息
//     */
//    public void freightInfo(Observer subscriber, String token, String ch) {
//        getService().freightInfo(token, ch)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 获取运费信息
//     */
//    public void goodsInfo(Observer subscriber, String token, String ch, String id) {
//        getService().goodsInfo(token, ch, id)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 微信支付信息
//     */
//    public void wxRegisterPayInfo(Observer subscriber, String token, String ch, String groupnum) {
//        getService().wxRegisterPayInfo(token, ch, groupnum)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 视频详情
//     */
//    public void videoSpeechDetail(Observer subscriber, String token, String ch, String id) {
//        getService().videoSpeechDetail(token, ch, id)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 支付宝支付信息
//     */
//    public void aliPayRegisterInfo(Observer subscriber, String token, String ch, String groupnum) {
//        getService().aliPayRegisterInfo(token, ch)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 获取验证码
//     */
//    public void smsCode(Observer subscriber, String ch, String ph, String smsType) {
//        getService().smsCode(ch, ph, smsType)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 获取验证码
//     */
//    public void groupList(Observer subscriber, String ch, String roomid) {
//        getService().groupList(ch, roomid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 注册
//     */
//    public void registerInfo(Observer subscriber, String ch, String mobile, String sms, String password
//            , String nikename, String openid, String dtype, String imgurl, String pid) {
//        getService().registerInfo(ch, mobile, sms, password, nikename, openid, dtype, imgurl, pid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 找回密码
//     */
//    public void findPwd(Observer subscriber, String ch, String mobile, String sms, String password
//            , String renewPwd) {
//        getService().findPwd(ch, mobile, sms, password, renewPwd)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 专家讲堂，直播
//     */
//    public void expertTeach(Observer subscriber, String ch, String hospitalid, String name, String type
//            , int page, String size) {
//        getService().expertTeach(ch, hospitalid, name, type, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 图文资讯
//     */
//    public void picNewsList(Observer subscriber, String ch, String hospitalid, String name, String type
//            , int page, String size) {
//
//        getService().picNewsList(ch, hospitalid, name, type, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 疾病百科
//     */
//    public void diseaseList(Observer subscriber, String ch, String name
//            , int page, String size) {
//
//        getService().diseaseList(ch, name, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 问诊 订单详情
//     */
//    public void askOrderDetail(Observer subscriber, String ch, String token
//            , String orderid) {
//        getService().askOrderDetail(ch, token, orderid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 免费问诊，图文问诊详情
//     */
//    public void getFreePicAskDetail(Observer subscriber, String ch, String token, String consultaid) {
//        getService().getFreePicAskDetail(ch, token, consultaid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//
//    /**
//     * 聊天列表
//     */
//    public void getChatInfo(Observer subscriber, String ch, String token, String consultaid) {
//        getService().getChatInfo(ch, token, consultaid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//
//    /**
//     * 商品 跟 服务 订单
//     */
//    public void serviceList(Observer subscriber, String ch, String token, String status
//            , String type, int page, String size) {
//        getService().serviceList(ch, token, status, type, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//
//    /**
//     * 聊天回复
//     */
//    public void chatReply(Observer subscriber, String ch, String token, String consultaid
//            , String content, String type, String usertype) {
//        getService().chatReply(ch, token, consultaid, content, type, usertype)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 添加回复
//     */
//    public void addEvaluation(Observer subscriber, String ch, String token, String relateid
//            , String ordercode, String content, String stardepict, String starservice, String starperformance
//            , String type, String imgs, String ordertype) {
//        getService().addEvaluation(ch, token, relateid, ordercode, content, stardepict, starservice, starperformance
//                , type, imgs, ordertype)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 患者信息
//     */
//    public void patientInfo(Observer subscriber, String ch, String token) {
//        getService().patientInfo(ch, token)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 医患帮帮团
//     */
//    public void helpList(Observer subscriber, String ch, String token) {
//        getService().helpList(ch, token)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//
//    /**
//     * 我的未读消息数量
//     */
//    public void unreadMsg(Observer subscriber, String ch, String token) {
//        getService().unreadMsg(ch, token)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 我的收藏列表
//     */
//    public void collectList(Observer subscriber, String ch, String token, String type, int page, String size) {
//        getService().collectList(ch, token, type, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//    /**
//     * 我的消息列表
//     */
//    public void myNewstList(Observer subscriber, String ch, String token, String type, int page, String size) {
//        getService().myNewstList(ch, token, type, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//    /**
//     * 更改手机号
//     */
//    public void changeTel(Observer subscriber, String ch, String token, String sms, String pwd, String phone) {
//        getService().changeTel(ch, token, sms, pwd, phone)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//    /**
//     * 修改密码
//     */
//    public void changePwd(Observer subscriber, String ch, String token, String sms, String newpwd, String renewpwd) {
//        getService().changePwd(ch, token, sms, newpwd, renewpwd)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//    /**
//     * 商品分类
//     */
//    public void goodsClassify(Observer subscriber, String ch) {
//        getService().goodsClassify(ch)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 我的 评价列表
//     */
//    public void commandList(Observer subscriber, String ch, String token, int page, String size) {
//        getService().commandList(ch, token, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 我的评价详情
//     */
//    public void commandInfo(Observer subscriber, String ch, String token, String id) {
//        getService().commandInfo(ch, token, id)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 商品 服务 评价详情
//     */
//    public void goodsCommandInfo(Observer subscriber, String ch, String token, String code) {
//        getService().goodsCommandInfo(ch, token, code)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 删除商品，服务订单
//     */
//    public void deleteGoodsOrder(Observer subscriber, String ch, String token, String id) {
//        getService().deleteGoodsOrder(ch, token, id)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 确认收货
//     */
//    public void confirmReceive(Observer subscriber, String ch, String token, String orderid) {
//        getService().confirmReceive(ch, token, orderid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 查看物流
//     */
//    public void getLogistic(Observer subscriber, String ch, String token, String orderid) {
//        getService().getLogistic(ch, token, orderid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 服务订单使用
//     */
//    public void useServiceOrder(Observer subscriber, String ch, String token, String orderid) {
//        getService().useServiceOrder(ch, token, orderid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 收藏(关注)类型  1-直播 2-资讯 3-讲堂 4-帖子 5-医生6-病友 7-文章
//     * 添加收藏, 关注
//     */
//    public void addCollection(Observer subscriber, String ch, String token
//            , String relateid, String type) {
//        getService().addCollection(ch, token, relateid, type)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 取消收藏 , 取消关注医生
//     */
//    public void removeCollection(Observer subscriber, String ch, String token
//            , String relateid, String type) {
//        getService().removeCollection(ch, token, relateid, type)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 商品详情
//     */
//    public void goodsList(Observer subscriber, String ch, String keys, String classid, int page, String size) {
//        getService().goodsList(ch, keys, classid, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 积分记录
//     */
//    public void pointsHistory(Observer subscriber, String ch, String token, String fromtype, int page, String size) {
//        getService().pointsHistory(ch, token, fromtype, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 搜索医生列表
//     */
//    public void searchDoctor(Observer subscriber, String ch, String name, String hosptialid, int page, String size) {
//        getService().searchDoctor(ch, name, hosptialid, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 搜索问诊列表
//     */
//    public void searchInquiry(Observer subscriber, String ch, String name, String hosptialid, int page, String size) {
//        getService().searchInquiry(ch, name, hosptialid, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 搜索文章列表
//     */
//    public void searchArticle(Observer subscriber, String ch, String name, String hosptialid, int page, String size) {
//        getService().searchArticle(ch, name, hosptialid, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 搜索医院列表
//     */
//    public void searchHospital(Observer subscriber, String ch, String name, int page, String size) {
//        getService().searchHospital(ch, name, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 精彩问诊
//     */
//    public void hotConsult(Observer subscriber, String ch, String hospitalid, int page, String size) {
//        getService().hotConsult(ch, hospitalid, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 专家讲堂
//     */
//    public void speechList(Observer subscriber, String ch, String hospitalid, int page, String size) {
//        getService().speechList(ch, hospitalid, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 医院列表
//     */
//    public void hospitalList(Observer subscriber, String ch, String type, String hospitalname, String longitude
//            , String latitude, String hostype, String hosgrade) {
//        getService().hospitalList(ch, type, hospitalname, longitude, latitude, hostype, hosgrade)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//    /**
//     * 医院类型列表
//     */
//    public void hospitalTypeList(Observer subscriber, String ch) {
//        getService().hospitalTypeList(ch)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 挂号科室选择
//     */
//    public void menzenList(Observer subscriber, String ch, String hospitalid) {
//        getService().menzenList(ch, hospitalid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 医院详情
//     */
//    public void hospitalInfo(Observer subscriber, String ch, String hospitalid) {
//        getService().hospitalInfo(ch, hospitalid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 挂号医生类型
//     */
//    public void doctorTypeList(Observer subscriber, String ch) {
//        getService().doctorTypeList(ch)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 查询号源
//     */
//    public void orderNumList(Observer subscriber, String ch, String hospitalid, String departid, String menzhenid
//            , String timedata, String timetype, String doctypeid, String doctorid) {
//        getService().orderNumList(ch, hospitalid, departid, menzhenid, timedata, timetype, doctypeid, doctorid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//
//    /**
//     * 预约挂号
//     */
//    public void registerOrder(Observer subscriber, String ch, String token, String appointid, String departid
//            , String outdepartid, String doctorid, String appointtype, String checkpatienid
//            , String note, String imgs) {
//        getService().registerOrder(ch, token, appointid, departid, outdepartid, doctorid, appointtype, checkpatienid
//                , note, imgs)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 挂号订单列表
//     */
//    public void registerOrderList(Observer subscriber, String ch, String token) {
//        getService().registerOrderList(ch, token)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//
//    /**
//     * 挂号订单详情
//     */
//    public void orderInfo(Observer subscriber, String ch, String token, String id) {
//        getService().orderInfo(ch, token, id)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 挂号订单评价详情
//     */
//    public void registerCommentDetail(Observer subscriber, String ch, String token, String code) {
//        getService().registerCommentDetail(ch, token, code)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 问诊订单评价详情
//     */
//    public void inquiryCommentDetail(Observer subscriber, String ch, String token, String code) {
//        getService().inquiryCommentDetail(ch, token, code)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 删除挂号订单
//     */
//    public void deleteRegisterOrder(Observer subscriber, String ch, String token, String orderid) {
//        getService().deleteRegisterOrder(ch, token, orderid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 查询绑定信息
//     */
//    public void bindInfo(Observer subscriber, String token) {
//        getService().bindInfo(token)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 修改个人信息
//     */
//    public void updateInfo(Observer subscriber, String ch, String token, String imgurl
//            , String name, String birthday, String sex, String location, String address) {
//        getService().updateInfo(ch, token, imgurl, name, birthday, sex, location, address)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 添加信息反馈
//     */
//    public void feedback(Observer subscriber, String ch, String token, String content
//            , String type, String phone) {
//        getService().feedback(ch, token, content, type, phone)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 我咨询的医生列表
//     */
//    public void askDoctorList(Observer subscriber, String ch, String token, int page
//            , String size) {
//        getService().askDoctorList(ch, token, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 我关注的医生列表
//     */
//    public void attentionDoctorList(Observer subscriber, String ch, String token, int page
//            , String size) {
//        getService().attentionDoctorList(ch, token, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 视频详情的评价列表
//     */
//    public void videoCommentList(Observer subscriber, String ch, String token, String id, int page
//            , String size) {
//        getService().videoCommentList(ch, token, id, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
////    /**
////     * 关注医生
////     */
////    public void attentionDoctor(Observer subscriber, String ch, String token
////            , String doctorid) {
////        getService().addCollection(ch, token, doctorid)
////                .compose(schedulersTransformer())
////                .subscribe(subscriber);
////
////    }
//
//    /**
//     * 点赞
//     */
//    public void good(Observer subscriber, String token, String type
//            , String relateid) {
//        getService().good(token, type, relateid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 点赞
//     */
//    public void removeGood(Observer subscriber, String token, String type
//            , String relateid) {
//        getService().removeGood(token, type, relateid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//
////    /**
////     * 取消关注医生
////     */
////    public void noAttentionDoctor(Observer subscriber, String ch, String token
////            , String doctorid, String type) {
////        getService().noAttentionDoctor(ch, token, doctorid, type)
////                .compose(schedulersTransformer())
////                .subscribe(subscriber);
////
////    }
//
//    /**
//     * 添加回复
//     */
//    public void reply(Observer subscriber, String ch, String token
//            , String id, String content) {
//        getService().reply(ch, token, id, content)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
////    /**
////     * 添加评论
////     */
////    public void evaluate(Observer subscriber, String ch, String token
////            , String relateid, String ordercode, String content
////            , String stardepict, String starservice, String starperformance
////            , String type, String imgs, String ordertype) {
////        getService().evaluate(ch, token, relateid, ordercode, content, stardepict
////                , starservice, starperformance, type, imgs, ordertype)
////                .compose(schedulersTransformer())
////                .subscribe(subscriber);
////
////    }
//
//    /**
//     * 我的评论列表
//     */
//    public void commentList(Observer subscriber, String ch, String token
//            , int page, String size) {
//        getService().commentList(ch, token, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 取消挂号订单
//     */
//    public void cancleOrder(Observer subscriber, String ch, String token
//            , String id, String status, String reason) {
//        getService().cancleOrder(ch, token, id, status, reason)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 取消商品，服务订单
//     */
//    public void cancleGoodsOrder(Observer subscriber, String ch, String token
//            , String orderid, String status, String reason) {
//        getService().cancleGoodsOrder(ch, token, orderid, status, reason)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 售后寄回地址
//     */
//    public void refundAddress(Observer subscriber) {
//        getService().refundAddress()
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 挂号申请退款
//     */
//    public void payBack(Observer subscriber, String ch, String token
//            , String id, String status, String reason, String imgs) {
//        getService().payBack(ch, token, id, status, reason, imgs)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 问诊申请退款
//     */
//    public void inquiryRefund(Observer subscriber, String ch, String token
//            , String orderId, String status, String reason, String imgs) {
//        getService().inquiryRefund(ch, token, orderId, status, reason, imgs)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 商品 申请退款
//     */
//    public void goodsRefund(Observer subscriber, String ch, String token
//            , String orderid, String reason, String imgs, String type) {
//        getService().goodsRefund(ch, token, orderid, reason, imgs, type)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 查询全部收货地址
//     */
//    public void allAddress(Observer subscriber, String ch, String token) {
//        getService().allAddress(ch, token)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 设为默认地址
//     */
//    public void setDefaultAddress(Observer subscriber, String ch, String token, String id) {
//        getService().setDefaultAddress(ch, token, id)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 删除地址
//     */
//    public void deleteAddress(Observer subscriber, String ch, String token, String id) {
//        getService().deleteAddress(ch, token, id)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 家人详情
//     */
//    public void familyDetail(Observer subscriber, String ch, String token, String id) {
//        getService().familyDetail(ch, token, id)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 我的申请列表
//     */
//    public void requestList(Observer subscriber, String ch, String token, String status, int page, String size) {
//        getService().requestList(ch, token, status, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 添加收货地址
//     */
//    public void addAddress(Observer subscriber, String ch, String token, String name, String mobile
//            , String provinceCode, String cityCode, String areaCode, String provinceName, String cityName, String areaName, String address, String isDefault) {
//        getService().addAddress(ch, token, name, mobile, provinceCode, cityCode, areaCode, provinceName, cityName, areaName, address, isDefault)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 修改收货地址
//     */
//    public void modifyAddress(Observer subscriber, String ch, String token, String id, String name, String mobile
//            , String provinceCode, String cityCode, String areaCode, String provinceName, String cityName, String areaName, String address, String isDefault) {
//        getService().modifyAddress(ch, token, id, name, mobile, provinceCode, cityCode, areaCode, provinceName, cityName, areaName, address, isDefault)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//
//    /**
//     * 结束问诊
//     */
//    public void endInquiry(Observer subscriber, String ch, String token, String consultaid) {
//        getService().endInquiry(ch, token, consultaid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//
//    /**
//     * 查看问诊小结
//     */
//    public void inquirySummaryReview(Observer subscriber, String ch, String token, String consultaid) {
//        getService().inquirySummaryReview(ch, token, consultaid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 医院科室列表
//     */
//    public void hospitalDeartmentList(Observer subscriber, String ch, String hospitalid) {
//        getService().hospitalDeartmentList(ch, hospitalid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//
//    }
//
//    /**
//     * 医院评论列表
//     */
//    public void hospitalCommentList(Observer subscriber, String ch, String token
//            , String hospitalid, int page, String size) {
//        getService().hospitalCommentList(ch, token, hospitalid, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//
//    /**
//     * 邀请列表
//     */
//    public void inviteList(Observer subscriber, String ch, String token
//            , int page, String size) {
//        getService().inviteList(ch, token, page, size)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//
//    /**
//     * 订单详情
//     */
//    public void goodsOrderInfo(Observer subscriber, String ch, String token
//            , String orderId) {
//        getService().goodsOrderInfo(ch, token, orderId)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//    /**
//     * 添加免费接送预约信息
//     */
//    public void freeTransfer(Observer subscriber, String ch, String token
//            , String name, String peoplecount, String phone, String address, String shuttletime, String hospitalid) {
//        getService().freeTransfer(ch, token, name, peoplecount, phone, address
//                , shuttletime, hospitalid)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//
//    /**
//     * 添加商品订单
//     */
//    public void goodsOrder(Observer subscriber, String ch, String token
//            , String addrid, String totalmoney, String freightmoney, String usepoints, String skuid, String skuprice
//            , String points, String skutype, String skucount, String remark) {
//        getService().goodsOrder(ch, token, addrid, totalmoney, freightmoney, usepoints
//                , skuid, skuprice, points, skutype, skucount, remark)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//
//    /**
//     * 分享成功后调用 用来增加积分
//     */
//    public void shareSuccessIntegralGress(Observer subscriber, String token) {
//        getService().shareSuccessIntegralGress(token)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//
//    /**
//     * 根据id查询文章资讯标题
//     */
//    public void getShareTileByid(Observer subscriber, String id) {
//        getService().getShareTileByid(id)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//    /**
//     * 如何赚取积分
//     */
//    public void howGetPoints(Observer subscriber, String ch, String token) {
//        getService().howGetPoints(ch, token)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//    /**
//     * 签到
//     */
//    public void checkIn(Observer subscriber, String ch, String token) {
//        getService().checkIn(ch, token)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//    /**
//     * 新版本检测
//     */
//    public void checkUpdate(Observer subscriber, String ch, String type) {
//        getService().checkUpdate(ch, type)
//                .compose(schedulersTransformer())
//                .subscribe(subscriber);
//    }
//
//
////
////    /**
////     * 资讯详情
////     */
////    public void infoDetail(Observer subscriber, String ch, String token, String id) {
////        getService().infoDetail(ch, token, id)
////                .compose(schedulersTransformer())
////                .subscribe(subscriber);
////
////    }

}
