package com.rbkmoney.dudoser.service;

import com.rbkmoney.damsel.domain.Party;
import com.rbkmoney.damsel.domain.Shop;
import com.rbkmoney.damsel.payment_processing.*;
import lombok.RequiredArgsConstructor;
import org.apache.thrift.TException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class PartyManagementService {

    private final PartyManagementSrv.Iface hellgateClient;

    @Cacheable("shops")
    public String getShopUrl(String partyId, String shopId, long revision) {
        try {
            UserInfo userInfo = new UserInfo("admin", UserType.internal_user(new InternalUser()));
            PartyRevisionParam partyRevisionParam = new PartyRevisionParam();
            partyRevisionParam.setRevision(revision);
            Party party = hellgateClient.checkout(userInfo, partyId, partyRevisionParam);
            Shop shop = party.getShops().get(shopId);

            if (shop == null) {
                throw new TException(String.format("Shop not found, partyId='%s', shopId='%s'", partyId, shopId));
            }

            if (shop.getLocation().isSetUrl()) {
                String url = shop.getLocation().getUrl();
                return StringUtils.isEmpty(url) ? null : url;
            }
        } catch (TException e) {
            throw new RuntimeException(
                    String.format("Unable to checkout Party by partyId: %s shopId: %s revision: %d", partyId, shopId,
                            revision), e);
        }
        return null;
    }

    public String getShopUrl(String partyId, String shopId, String timeStamp) {
        try {
            UserInfo userInfo = new UserInfo("admin", UserType.internal_user(new InternalUser()));
            PartyRevisionParam partyRevisionParam = new PartyRevisionParam();
            partyRevisionParam.setTimestamp(timeStamp);
            Party party = hellgateClient.checkout(userInfo, partyId, partyRevisionParam);
            Shop shop = party.getShops().get(shopId);

            if (shop == null) {
                throw new TException(String.format("Shop not found, partyId='%s', shopId='%s'", partyId, shopId));
            }

            if (shop.getLocation().isSetUrl()) {
                String url = shop.getLocation().getUrl();
                return StringUtils.isEmpty(url) ? null : url;
            }
        } catch (TException e) {
            throw new RuntimeException(
                    String.format("Unable to checkout Party by partyId: %s shopId: %s timestamp: %s", partyId, shopId,
                            timeStamp), e);
        }
        return null;
    }
}
