package com.example.finalsemprojectbuyer.Data;

public class SubscriptionData
{
    public static final String TITLE = "title";
    public static final String BODY = "body";
    public static final String SUBSCRIPTION = "subscriptionData";
    public static final String MESSAGE = "messagePacket";
    public static final String PACKET_TYPE = "Packet_type";
    public static final String TOPIC = "to";
    public static final String SALES = "salesPacket";

    public static class topic
    {
        public static final String MESSAGE = "/topic_message/";
        public static final String SALES = "/topic_sales/";
    }
    public static class MessagePacket
    {
        public static final String SENDER_ID = "sender_id";
        public static final String RECEIVER_ID = "receiver_id";
    }

    public static class SalesPacket
    {
        public static final String SELLER_ID = "Seller_id";
        public static final String BUYER_ID = "Buyer_id";
        public static final String PRODUCT_ID = "Product_id";
        public static final String NOTIFICATION_ID = "Notification_id";
        public static final String NOTIFICATION_ID_PURCHASE_SUCCESS = "Notification_id_purchase_success";
        public static final String NOTIFICATION_ID_TRANSACTION_SUCCESS = "Notification_id_transaction_success";
        public static final String NOTIFICATION_ID_PAYMENT_SUCCESS = "Notification_id_payment_success";
    }

}
