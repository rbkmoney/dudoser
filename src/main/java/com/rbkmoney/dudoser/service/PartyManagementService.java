package com.rbkmoney.dudoser.service;

import com.rbkmoney.damsel.domain.Party;
import com.rbkmoney.damsel.domain.Shop;
import com.rbkmoney.damsel.payment_processing.InternalUser;
import com.rbkmoney.damsel.payment_processing.PartyManagementSrv;
import com.rbkmoney.damsel.payment_processing.UserInfo;
import com.rbkmoney.damsel.payment_processing.UserType;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @since 20.06.17
 **/

@Service
public class PartyManagementService {

    @Autowired
    PartyManagementSrv.Iface hellgateClient;

    public String getShopUrl(String partyId, String shopId, String timeStamp) {
        try {
            UserInfo userInfo = new UserInfo("admin", UserType.internal_user(new InternalUser()));
            Party party = hellgateClient.checkout(userInfo, partyId, timeStamp);
            Shop shop = party.getShops().get(shopId);

            if (shop == null) {
                throw new TException(String.format("Shop not found, partyId='%s', shopId='%s'", partyId, shopId));
            }

            if (shop.getLocation().isSetUrl()) {
                String url = shop.getLocation().getUrl();
                return StringUtils.isEmpty(url) ? null : url;
            }
        } catch (TException e) {
            throw new RuntimeException(String.format("Unable to checkout Party by partyId: %s shopId: %s timestamp: %s", partyId, shopId, timeStamp), e);
        }
        return null;
    }
}
