package com.dao;

import com.pojo.UserRedPacket;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRedPacketDao {
    /*
    抢红包
     */
    public int grapRedPacket(UserRedPacket userRedPacket);
}
