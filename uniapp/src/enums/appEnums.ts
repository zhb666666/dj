//菜单主题类型
export enum ThemeEnum {
    LIGHT = 'light',
    DARK = 'dark'
}

// 客户端
export enum ClientEnum {
    MP_WEIXIN = 1, // 微信-小程序
    OA_WEIXIN = 2, // 微信-公众号
    H5 = 3, // H5
    IOS = 5, //苹果
    ANDROID = 6 //安卓
}

export enum SMSEnum {
    LOGIN = 101,
    BIND_MOBILE = 102,
    CHANGE_MOBILE = 103,
    FIND_PASSWORD = 104
}

export enum SearchTypeEnum {
    HISTORY = 'history'
}

// 用户资料
export enum FieldType {
    NONE = '',
    AVATAR = 'avatar',
    USERNAME = 'username',
    NICKNAME = 'nickname',
    SEX = 'sex'
}

// 支付结果
export enum PayStatusEnum {
    SUCCESS = 'success',
    FAIL = 'fail',
    PENDING = 'pending'
}

// 页面状态
export enum PageStatusEnum {
    LOADING = 'loading', // 加载中
    NORMAL = 'normal', // 正常
    ERROR = 'error', // 异常
    EMPTY = 'empty' // 为空
}
export enum PlayerStatusEnum {
	Pause = "pause",// 暂停
	Play = "play",// 播放
}

// 点赞类型
export enum LikeType {
	VIDEO = 1
}

// 评论类型
export enum CommentType {
	VIDEO = 1
}
// 问题类型
export enum QuestionType {
	RADIO = 1,
	CHECKBOX = 2,
	TEXT = 30
}
// 题目类型
export enum TopicType {
	RADIO = 10,
	CHECKBOX = 20,
	TEXT = 30,
	YES_OR_NO = 40
}
// 是否类型
export enum YesNoEnum {
	YES = 1,
	NO = 0
}

export enum IntegralLogTypeEnum{
    LOGIN = 10,
    ANSWER = 20,
    SHOP = 30
}
export enum integralShopOrderStatusEnum{
    NO_HANDLE = 1,
    HANDLING = 2,
    POSTED  = 3,
    FAILED = 4
}