package com.server;

public interface UserRedPacketService {
    /*
    保存抢红包的信息
    userId 抢红包的用户编号
    RedPacketId 红包编号
     */
    public int grapRedPacket(long redPacketId,long userId);
}
