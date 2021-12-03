# Azure Event Hub Integration Samples

This repository contains a bunch of sample projects for demonstrating how to publish/consume messages with Azure Event Hub in different ways:

- Azure Java SDK libraries vs community libraries
- AMQP 1.0, JMS over AMQP 1.0 and Kafka PLAINTEXT protocols
- Spring Boot
- Spring Cloud Stream

## Event Hub Configuration

You can either edit the placeholder variables in `application.yml` or pass the environment variables the table below to the specific Spring Boot Application at runtime.

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

## application.yml

The following is a sample configuration file content with Azure SDK `azure-spring-cloud-starter-eventhubs`.

```yaml
azure:
  credential:
    client-id: ${CLIENT_ID}
    client-secret: ${CLIENT_SECRET}
    tenant-id: ${TENANT_ID}
spring:
  cloud:
    azure:
      resource-group: ${RESOURCE_GROUP}
      eventhub:
        namespace: ${EH_NAMESPACE}
    stream:
      bindings:
        consume-in-0:
          destination: ${EH_TOPIC:my-topic}
          group: $Default
        supply-out-0:
          destination: ${EH_TOPIC:my-topic}

      eventhub:
        bindings:
          consume-in-0:
            consumer:
              checkpoint-mode: MANUAL
      function:
        definition: consume;supply;
      poller:
        initial-delay: 0
        fixed-delay: 1000
```

