
## Azure Event Hub for Kafka
There are some differences in functionalities between Azure Event Hub and Azure Event Hub for Kafka.
Check Azure documentation for details at https://docs.microsoft.com/en-us/azure/event-hubs/event-hubs-for-kafka-ecosystem-overview.

### References
- https://docs.microsoft.com/en-us/azure/event-hubs/event-hubs-for-kafka-ecosystem-overview
- https://github.com/Azure/azure-event-hubs/tree/master/samples/Java
- https://docs.microsoft.com/en-us/azure/event-hubs/apache-kafka-developer-guide
- https://docs.microsoft.com/en-us/azure/developer/java/spring-framework/configure-spring-cloud-stream-binder-java-app-kafka-azure-event-hub


## Azure Resource Dependencies
### Resource Group

The related Azure resources like Event Hub namespace, topics and storage accounts need to reside
in the same resource group, otherwise the identity library won't work.

### Security
#### Authentication - application service principal
The following items are needed:
- tenant id
- client id
- client secret
- resource group

#### Access roles
|Azure Resource|Role|
|----|----
|Event Hub topic| Azure Event Hubs Data Receiver/Producer

