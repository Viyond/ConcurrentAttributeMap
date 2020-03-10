# ConcurrentAttributeMap
Access a map with internal array index.
map操作数组化，高并发下大量的map操作会成为计算热点，通过转化为内部数组操作，减少cpu消耗。

工作机制说明
1、key类型由String改为自定义的AttributeKey，AttributeKey会在初始化时候去AttributeNameSpace中申请一个固定id；
2、map类型由HashMap改为ConcurrentAttributeMap，内部使用数组存放数据；
3、当使用AttributeKey访问时，会使用关联的固定id作为数组index进行数组访问，避免了hash计算等一系列操作。

对比HashMap和ConcurrentAttributeMap，性能提升30%
PUT：
HashMap put time(ms) : 262
ArrayMap put time(ms) : 185

GET：
HashMap get time(ms) : 184
ArrayMap get time(ms) : 126
