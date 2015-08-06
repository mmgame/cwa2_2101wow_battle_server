package com.cwa.simuiationimpl.constant;

/**
 * 常量
 * 
 * @author tzy
 * 
 */
public class SimuiationConstant {
	public static float Ellipse_Ratio = 2.0f;//客户端显示椭圆比例(a:b)
	public static int SkillIdBase = 100000;//
	public static int MaxSkill = 62;//
	public static int SmallSkill = 63;//
	public static int CommanSkill = 61;//
	public static int Dimensions = 2; // 坐标维度
	public static int ClientFrameTime = 1000 / 60; // 客户端每帧时间ms
	public static int SystemUpdateTime = 12; // 系统update偏差时间（毫秒）
	public static final int Type_Divisors = 100;// 状态类型因数
	public static final int PriorityQueue_Capacity = 11;// 优先队列初始化容量
	public static final String STimeOverTaskKey = "STimeOverTask";// 结束任务key
	public static final int DistanceTolerance = 10;// 距离容错
	public static final int RandomValue = 100000;// 随机值
	public static final int InitEnergy = 100;// 初始能量值
	public static final float Millisecond = 1000;// 毫秒.
	public static final int SubType = 10000;// 对象子类型所占位数
	public static final int Percent = 100;// 百分比
	public static final float SuckRate = 0.2f;// 吸血百分比
	public static final int PositionRandom = 50; // 位置偏移量
	
	// ------------------------------
	// 监听器key
	public static final String ListenerKey_System = "SystemListener";
	public static final String ListenerKey_ClientMgr = "ClientMgrListener";
	public static final String ListenerKey_IEventMgr = "IEventMgrListener";

	public void setDimensions(int dimensions) {
		Dimensions = dimensions;
	}

	public void setClientFrameCount(int frameCount) {
		ClientFrameTime = 1000 / frameCount;
	}

	public void setSystemUpdateTime(int systemUpdateTime) {
		SystemUpdateTime = systemUpdateTime;
	}
}
