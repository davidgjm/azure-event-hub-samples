


### References
- https://github.com/Azure/azure-event-hubs/tree/master/samples/Java


## Azure Resource Dependencies

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
|Storage account blob container|Storage Blob Data Contributor
|Event Hub topic| Azure Event Hubs Data Receiver/Producer


### Spring Cloud Stream Eventhub Binder 
The following Azure resources are needed in order to make the eventhub binder run successfully:
- Azure Event Hub Namespace with existing topics
- Azure Storage Account & Blob Container for checkpointing