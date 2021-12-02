## Configuration

### Environment Variables
|Environment Name|Description|Example Value|
|----|----|----|
|CLIENT_ID|Azure AD app client id|`4007b07a-3283-4d1b-8513-e816dded9c54`
|CLIENT_SECRET|Azure AD app client secret|
|TENANT_ID|Azure AD tenant id|`dd14ab2e-a222-4c5f-b200-59680bd50fff`|
|SAS_POLICY_NAME|Shared access policy name. used as username| topic-owner|
|SAS_POLICY_KEY|Shared access key. used as password|`random string`
|RESOURCE_GROUP|Azure resource group name|`my-resource-group`
|EH_HOST_NAME|Azure Event Hub host name|`my-event-hub.servicebus.windows.net`|
|EH_AMQP_PORT|Azure Event Hub AMQP port. `5671` for amqp over tls|`5671`
|EH_NAMESPACE|Azure Event Hub namespace|`my-event-hub-namespace`|
|EH_TOPIC|Azure Event Hub topic|`my-topic`
|SUBSCRIPTION_ID|Your Azure subscription id|`dd14ab2e-a222-4c5f-b200-59680bd50fff`
