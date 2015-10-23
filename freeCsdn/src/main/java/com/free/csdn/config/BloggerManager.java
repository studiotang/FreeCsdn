package com.free.csdn.config;

import android.content.Context;

import com.free.csdn.bean.Blogger;
import com.free.csdn.config.CategoryManager.CategoryName;
import com.free.csdn.db.BloggerDao;
import com.free.csdn.util.SpfUtils;

/**
 * Andoird博主管理
 * 
 * @author tangqi
 * @data 2015年7月9日下午4:41:18
 */

public class BloggerManager {

	/**
	 * 执行插入数据库操作
	 * 
	 * @param bloggerDb
	 * @param blogger
	 * @param array
	 */
	private void insertBlogger(BloggerDao bloggerDb, Blogger blogger, String[] array) {
		if (array == null || array.length < 6 || blogger == null || bloggerDb == null) {
			return;
		}

		blogger.setUserId(array[0]);
		blogger.setTitle(array[1]);
		blogger.setDescription(array[2]);
		blogger.setImgUrl(array[3]);
		blogger.setLink(array[4]);
		blogger.setType(array[5]);
		blogger.setIsTop(0);
		blogger.setIsNew(0);
		blogger.setCategory(CategoryName.MOBILE);
		blogger.setUpdateTime(System.currentTimeMillis());

		bloggerDb.insert(blogger);
	}

	/**
	 * 删除所有博客
	 * 
	 * @param bloggerDb
	 */
	private void deleteAllBlogger(BloggerDao bloggerDb, String type) {
		bloggerDb.deleteAll();
	}

	/**
	 * 初始化博主信息
	 * 
	 * @param context
	 */
	public void init(Context context, BloggerDao bloggerDb, String type) {

		if (!((Boolean) SpfUtils.get(context, ExtraString.IS_FIRST, true)))
			return;

		deleteAllBlogger(bloggerDb, type);
		bloggerDb.init(type);

		Blogger blogger = new Blogger();
		String[] array = new String[6];

		array[0] = "lmj623565791";
		array[1] = "张鸿洋";
		array[2] = "生命不息，奋斗不止，万事起于忽微，量变引起质变";
		array[3] = "http://avatar.csdn.net/F/F/5/1_lmj623565791.jpg";
		array[4] = "http://blog.csdn.net/lmj623565791";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "yayun0516";
		array[1] = "张亚运";
		array[2] = "Technology changes life，Code writes everything.（小研快毕业，求引荐工作！谢谢）";
		array[3] = "http://avatar.csdn.net/D/6/D/1_yayun0516.jpg";
		array[4] = "http://blog.csdn.net/yayun0516";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "wwj_748";
		array[1] = "巫文杰";
		array[2] = "一个人走到任何境地全都是因为自己。";
		array[3] = "http://avatar.csdn.net/C/C/8/1_wwj_748.jpg";
		array[4] = "http://blog.csdn.net/wwj_748";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "coder_pig";
		array[1] = "庄培杰";
		array[2] = "时间一天天过去，我们终会因我们的努力或堕落变得丰富或苍白";
		array[3] = "http://avatar.csdn.net/C/2/C/1_zpj779878443.jpg";
		array[4] = "http://blog.csdn.net/coder_pig";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "gao_chun";
		array[1] = "高纯";
		array[2] = "一 大 坨 代 码 ！";
		array[3] = "http://avatar.csdn.net/8/1/E/1_gao_chun.jpg";
		array[4] = "http://blog.csdn.net/gao_chun";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "jiangwei0910410003";
		array[1] = "姜维";
		array[2] = "jiangwei0910410003的专栏";
		array[3] = "http://avatar.csdn.net/7/1/0/1_jiangwei0910410003.jpg";
		array[4] = "http://blog.csdn.net/jiangwei0910410003";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "guolin_blog";
		array[1] = "郭霖";
		array[2] = "每当你在感叹，如果有这样一个东西就好了的时候，请注意，其实这是你的机会";
		array[3] = "http://avatar.csdn.net/8/B/B/1_sinyu890807.jpg";
		array[4] = "http://blog.csdn.net/guolin_blog";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "singwhatiwanna";
		array[1] = "任玉刚";
		array[2] = "有创新精神的Android技术分享者";
		array[3] = "http://avatar.csdn.net/0/2/C/1_singwhatiwanna.jpg";
		array[4] = "http://blog.csdn.net/singwhatiwanna";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "cym492224103";
		array[1] = "陈宇明";
		array[2] = "分享既是快乐！更多源码请查看javaapk.com.";
		array[3] = "http://avatar.csdn.net/F/A/3/1_cym492224103.jpg";
		array[4] = "http://blog.csdn.net/cym492224103";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "eclipsexys";
		array[1] = "徐宜生";
		array[2] = "路漫漫其修远兮 吾将上下而求索";
		array[3] = "http://avatar.csdn.net/A/6/8/1_x359981514.jpg";
		array[4] = "http://blog.csdn.net/eclipsexys";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "zhaokaiqiang1992";
		array[1] = "赵凯强";
		array[2] = "专注Android移动开发，热爱分享，支持开源";
		array[3] = "http://avatar.csdn.net/C/6/8/1_bz419927089.jpg";
		array[4] = "http://blog.csdn.net/zhaokaiqiang1992";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "xiaanming";
		array[1] = "夏安明";
		array[2] = "专注移动互联网开发，注重实战，是软件开发的一柄利剑";
		array[3] = "http://avatar.csdn.net/4/F/6/1_xiaanming.jpg";
		array[4] = "http://blog.csdn.net/xiaanming";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "lincyang";
		array[1] = "杨烈";
		array[2] = "做一个好设计，写一手好程序，经营一个好产品。";
		array[3] = "http://avatar.csdn.net/7/8/A/1_lincyang.jpg";
		array[4] = "http://blog.csdn.net/lincyang";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "harvic880925";
		array[1] = "张恩伟";
		array[2] = "当乌龟有了梦想……";
		array[3] = "http://avatar.csdn.net/0/D/3/1_harvic880925.jpg";
		array[4] = "http://blog.csdn.net/harvic880925";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "yuanzeyao";
		array[1] = "袁泽瑶";
		array[2] = "每只菜鸟都有雄鹰的梦想，每只雄鹰都有菜鸟的曾经。菜鸟 ，为梦想而战....";
		array[3] = "http://avatar.csdn.net/1/4/1/1_yuanzeyao2008.jpg";
		array[4] = "http://blog.csdn.net/yuanzeyao";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "jason0539";
		array[1] = "刘贞会";
		array[2] = "坚持做自己懒得做但是正确的事情，你就能得到别人想得到却得不到的东西。";
		array[3] = "http://avatar.csdn.net/9/B/C/1_jason0539.jpg";
		array[4] = "http://blog.csdn.net/jason0539";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "hjd_love_zzt";
		array[1] = "黄俊东";
		array[2] = "为中华民族软件产业之崛起而埋首敲代码。在这里，为中华民族的崛起贡献自己的全部力量·······";
		array[3] = "http://avatar.csdn.net/1/F/5/1_caihongshijie6.jpg";
		array[4] = "http://blog.csdn.net/hjd_love_zzt";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "arui319";
		array[1] = "高磊";
		array[2] = "①Enjoy Coding ②Enjoy Life ③gaolei021 # gmail.com";
		array[3] = "http://avatar.csdn.net/9/2/1/1_arui319.jpg";
		array[4] = "http://blog.csdn.net/arui319";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "hellogv";
		array[1] = "张国威";
		array[2] = "关注互联网金融 、硬件互联网化...";
		array[3] = "http://avatar.csdn.net/F/7/E/1_hellogv.jpg";
		array[4] = "http://blog.csdn.net/hellogv";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "xyz_lmn";
		array[1] = "张兴业";
		array[2] = "会设计的程序员和会编程的设计师,专注移动互联网。";
		array[3] = "http://avatar.csdn.net/E/D/5/1_xyz_lmn.jpg";
		array[4] = "http://blog.csdn.net/xyz_lmn";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "xlgen157387";
		array[1] = "徐刘根";
		array[2] = "xlgen157387的专栏";
		array[3] = "http://avatar.csdn.net/D/7/D/1_u010870518.jpg";
		array[4] = "http://blog.csdn.net/xlgen157387";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "rootusers";
		array[1] = "仝利";
		array[2] = "道可道，非常道，名可名，非常名。";
		array[3] = "http://avatar.csdn.net/D/A/9/1_rootusers.jpg";
		array[4] = "http://blog.csdn.net/rootusers";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "wangjinyu501";
		array[1] = "王鹏";
		array[2] = "优秀是一种习惯 认真是一种方式";
		array[3] = "http://avatar.csdn.net/D/1/4/1_wangjinyu501.jpg";
		array[4] = "http://blog.csdn.net/wangjinyu501";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "way_ping_li";
		array[1] = "李伟平";
		array[2] = "爱生活...爱Android...";
		array[3] = "http://avatar.csdn.net/5/0/9/1_weidi1989.jpg";
		array[4] = "http://blog.csdn.net/way_ping_li";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "dawanganban";
		array[1] = "李小强";
		array[2] = "http://www.sunhome.org.cn/";
		array[3] = "http://avatar.csdn.net/D/F/D/1_lxq_xsyu.jpg";
		array[4] = "http://blog.csdn.net/dawanganban";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "p106786860";
		array[1] = "彭呈祥";
		array[2] = "光荣是在于平淡，艰巨是在于漫长。要把有限的生命用于无限的逆袭...";
		array[3] = "http://avatar.csdn.net/0/7/F/1_p106786860.jpg";
		array[4] = "http://blog.csdn.net/p106786860";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "rain_butterfly";
		array[1] = "王世昌";
		array[2] = "Stay hungry，Stay foolish。";
		array[3] = "http://avatar.csdn.net/2/D/B/1_rain_butterfly.jpg";
		array[4] = "http://blog.csdn.net/rain_butterfly";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "yanbober";
		array[1] = "晏博";
		array[2] = "知道+做到=得到";
		array[3] = "http://avatar.csdn.net/7/3/D/1_yanbober.jpg";
		array[4] = "http://blog.csdn.net/yanbober";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "yanzi1225627";
		array[1] = "晏国淇";
		array[2] = "yanzi1225627的专栏";
		array[3] = "http://avatar.csdn.net/1/4/F/1_yanzi1225627.jpg";
		array[4] = "http://blog.csdn.net/yanzi1225627";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "t12x3456";
		array[1] = "田啸";
		array[2] = "Never put off the work till tomorrow what you can put off today.";
		array[3] = "http://avatar.csdn.net/1/8/C/1_t12x3456.jpg";
		array[4] = "http://blog.csdn.net/t12x3456";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "wdaming1986";
		array[1] = "王宪明";
		array[2] = "Android学习交流群：158788096！酷爱Andorid系统开发！";
		array[3] = "http://avatar.csdn.net/C/F/4/1_wdaming1986.jpg";
		array[4] = "http://blog.csdn.net/wdaming1986";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "bboyfeiyu";
		array[1] = "何红辉";
		array[2] = "LIFE IS LIKE A BATTLE.";
		array[3] = "http://avatar.csdn.net/3/5/2/1_bboyfeiyu.jpg";
		array[4] = "http://blog.csdn.net/bboyfeiyu";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "nupt123456789";
		array[1] = "郑海波";
		array[2] = "业精于勤而荒于嬉，行成于思而毁于随 http://www.mobctrl.net";
		array[3] = "http://avatar.csdn.net/C/2/1/1_nuptboyzhb.jpg";
		array[4] = "http://blog.csdn.net/nupt123456789";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "Android_Tutor";
		array[1] = "魏祝林";
		array[2] = "Android_Tutor的专栏";
		array[3] = "http://avatar.csdn.net/3/F/7/1_android_tutor.jpg";
		array[4] = "http://blog.csdn.net/Android_Tutor";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "androidsecurity";
		array[1] = "贾志军";
		array[2] = "专注于Android手机平台安全--[欢迎加入Android安全实验室QQ群：296752155]";
		array[3] = "http://avatar.csdn.net/B/3/3/1_jiazhijun.jpg";
		array[4] = "http://blog.csdn.net/androidsecurity";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "reboot123";
		array[1] = "刘贤";
		array[2] = "第一：走自己的路；第二：不要八卦别人的做法；第三：把愤怒压到明天再发。";
		array[3] = "http://avatar.csdn.net/B/1/6/1_liuxian13183.jpg";
		array[4] = "http://blog.csdn.net/reboot123";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "lilu_leo";
		array[1] = "李鲁";
		array[2] = "努力做好一个手艺人！";
		array[3] = "http://avatar.csdn.net/A/D/3/1_aomandeshangxiao.jpg";
		array[4] = "http://blog.csdn.net/lilu_leo";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "hfahe";
		array[1] = "蒋宇捷";
		array[2] = "关注互联网、移动互联网的应用和趋势";
		array[3] = "http://avatar.csdn.net/A/2/D/1_hfahe.jpg";
		array[4] = "http://blog.csdn.net/hfahe";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "berryreload";
		array[1] = "杨江";
		array[2] = "分享个人在IT领域的心得体会，从企业移动信息化，统一通讯，到内存数据库，电子商务。";
		array[3] = "http://avatar.csdn.net/2/F/4/1_berryreload.jpg";
		array[4] = "http://blog.csdn.net/berryreload";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "nokiaguy";
		array[1] = "李宁";
		array[2] = "IT作家、讲师、技术顾问，主要研究领域：移动技术、编译器、Linux内核、电子电路。";
		array[3] = "http://avatar.csdn.net/2/E/4/1_nokiaguy.jpg";
		array[4] = "http://blog.csdn.net/nokiaguy";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "shiningxyy";
		array[1] = "炫姐姐";
		array[2] = "从北京到深圳，我和开发者在一起";
		array[3] = "http://avatar.csdn.net/B/7/0/1_shiningxyy.jpg";
		array[4] = "http://blog.csdn.net/shiningxyy";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "ostrichmyself";
		array[1] = "田甜";
		array[2] = "关注移动互联、物联网、云计算的Coder【C/C++/java ";
		array[3] = "http://avatar.csdn.net/E/1/5/1_ostrichmyself.jpg";
		array[4] = "http://blog.csdn.net/ostrichmyself";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "zhouhongyi";
		array[1] = "周鸿祎";
		array[2] = "360公司首席用户体验官，曾经的程序员现在的产品经理。";
		array[3] = "http://avatar.csdn.net/8/4/4/1_zhouhongyi.jpg";
		array[4] = "http://blog.csdn.net/zhouhongyi";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "leixiaohua1020";
		array[1] = "雷霄骅";
		array[2] = "一个广院工科生的视音频技术笔记";
		array[3] = "http://avatar.csdn.net/A/7/6/1_leixiaohua1020.jpg";
		array[4] = "http://blog.csdn.net/leixiaohua1020";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "lyq8479";
		array[1] = "柳峰";
		array[2] = "推荐关注微信公众号“小q机器人”(微信号：xiaoqrobot)、“微趣闻”（微信号：laoyao_89），QQ交流群：315010949";
		array[3] = "http://avatar.csdn.net/1/4/A/1_lyq8479.jpg";
		array[4] = "http://blog.csdn.net/lyq8479";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "ricohzhanglong";
		array[1] = "张龙";
		array[2] = "探寻未知";
		array[3] = "http://avatar.csdn.net/1/2/E/1_ricohzhanglong.jpg";
		array[4] = "http://blog.csdn.net/ricohzhanglong";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "LoveLion";
		array[1] = "刘伟";
		array[2] = "专注软件架构、设计模式、重构、UML和OOAD！";
		array[3] = "http://avatar.csdn.net/C/8/3/1_lovelion.jpg";
		array[4] = "http://blog.csdn.net/LoveLion";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "DLUTBruceZhang";
		array[1] = "张虎";
		array[2] = "echo 'are you happy ?' | cut -d ' ' -f 3 | tr -d '\r\n '";
		array[3] = "http://avatar.csdn.net/4/9/E/1_dlutbrucezhang.jpg";
		array[4] = "http://blog.csdn.net/DLUTBruceZhang";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "songjinshi";
		array[1] = "宋金时";
		array[2] = "士不可以不弘毅，任重而道远！你无法改变过去，却可以把握现在和未来，相信自己，无限可能！";
		array[3] = "http://avatar.csdn.net/7/C/E/1_songjinshi.jpg";
		array[4] = "http://blog.csdn.net/songjinshi";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "ameyume";
		array[1] = "巩玉敏";
		array[2] = "蓝蓝的天，白云朵朵。 White clouds in the blue sky.";
		array[3] = "http://avatar.csdn.net/3/2/4/1_ameyume.jpg";
		array[4] = "http://blog.csdn.net/ameyume";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "sunboy_2050";
		array[1] = "杨刚";
		array[2] = "欢迎访问我的新博客： http://blog.mimvp.com";
		array[3] = "http://avatar.csdn.net/C/6/F/1_sunboy_2050.jpg";
		array[4] = "http://blog.csdn.net/sunboy_2050";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "zouxy09";
		array[1] = "zouxy09的专栏";
		array[2] = "悲喜枯荣如是本无分别，当来则来，当去则去，随心，随性，随缘！-zouxy09@qq.com";
		array[3] = "http://avatar.csdn.net/9/0/6/1_zouxy09.jpg";
		array[4] = "http://blog.csdn.net/zouxy09";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "changemyself";
		array[1] = "心有灵犀鬼才心";
		array[2] = "个人需求是第一生产力！";
		array[3] = "http://avatar.csdn.net/E/4/B/1_changemyself.jpg";
		array[4] = "http://blog.csdn.net/changemyself";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "ctthuangcheng";
		array[1] = "ctthuangcheng";
		array[2] = "自学成才。。。。加油！";
		array[3] = "http://avatar.csdn.net/8/9/6/1_ctthunagchneg.jpg";
		array[4] = "http://blog.csdn.net/ctthuangcheng";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "Luoshengyang";
		array[1] = "老罗的Android之旅";
		array[2] = "爱生活，爱Android";
		array[3] = "http://avatar.csdn.net/5/6/E/1_luoshengyang.jpg";
		array[4] = "http://blog.csdn.net/Luoshengyang";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		array[0] = "fansy1990";
		array[1] = "fansy1990的专栏";
		array[2] = "一日一积累";
		array[3] = "http://avatar.csdn.net/A/8/A/1_fansy1990.jpg";
		array[4] = "http://blog.csdn.net/fansy1990";
		array[5] = type;
		insertBlogger(bloggerDb, blogger, array);

		SpfUtils.put(context, ExtraString.IS_FIRST, false);
		SpfUtils.put(context, ExtraString.BLOG_TYPE, type);
	}
}
