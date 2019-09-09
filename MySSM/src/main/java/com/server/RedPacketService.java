package com.server;

import com.pojo.RedPacket;

public interface RedPacketService {
    /*
    获取红包
     */
    public RedPacket getRedPacket(long id);

    /*
    扣除红包
     */
    public int decreaseRedPacket(long id);
}