package com.dao;

import com.pojo.RedPacket;
import org.springframework.stereotype.Repository;

@Repository
public interface RedPacketDao {
    /*
     * 获取红包信息
     * @Return 红包具体信息
     */
    public RedPacket getRedPacket(long id);

    /*
    扣减红包
     */
    public int decreaseRedPacket(long id);
}
