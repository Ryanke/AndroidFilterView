package adapterFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * 筛选器参数
 * Created by kerui on 2016/11/17.
 */

public class FilterConstant {
    public List<String> channelFilter = new ArrayList<>();
    public List<String> typeFilter = new ArrayList<>();
    public List<String> stateFilter = new ArrayList<>();
    public List<String> tagList = new ArrayList<>();
    public int DEFAULT_NULL = 0x1234; //列表区域以外点击
    public int ITEM_CLICK = 0x4321;//列表点击
    public int NULL_POINT = 0x321;//point 为空的情况

    private volatile static FilterConstant uniqueInstance;//利用一个静态变量来记录SingleTon类的唯一实例
    //其他有用的单件类的数据

    private FilterConstant() {
        initFilter();
    } //类外无法访问

    public static FilterConstant getInstance() {
        /*
         * 使用”双重检查加锁“,在getInstance中减少使用同步
         * 首先检查是否实例已经创建了，如果尚未创建，才进行同步；只有第一次访问getInstance会同步
        */
        if (uniqueInstance == null) {  //确保只有一个实例
            synchronized (FilterConstant.class) { //多线程的情况不会出现问题，线程同步问题
                if (uniqueInstance == null) {
                    uniqueInstance = new FilterConstant();//如果我们不需要这个实例，则永远不会产生
                }
            }
        }
        return uniqueInstance;
    }

    //其他有用的单件类的方法，单件类也可以是一般的类，具有一般的数据和方法
    private void initFilter() {
        stateFilter.add("全部");
        stateFilter.add("待处理");
        stateFilter.add("已处理");
        stateFilter.add("已驳回");
        typeFilter.add("全部");
        typeFilter.add("签约中心");
        typeFilter.add("驻点");
        typeFilter.add("上门");
        typeFilter.add("远程");
        channelFilter.add("全部");
        channelFilter.add("爱屋吉屋");
        channelFilter.add("链家");
        tagList.add("mChannelFilter");
        tagList.add("mStateFilter");
        tagList.add("mTypeFilter");

    }
}
