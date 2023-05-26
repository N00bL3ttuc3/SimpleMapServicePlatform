# 简易电子地图服务平台（SimpleMapServicePlatform）

本仓库是本人本科毕业设计的记录，也是突发感慨，来记录一下，是仿照百度地图API做一个完全开源的地图服务平台

## 系统介绍

设计并实现了一种基于WebGIS的电子地图服务平台，使之具有地图底图图层的管理、地点区域标记、逆向地理编码、多种路径规划问题的实现功能。前后端分离，利用Vue整合Leaflet、Openlayers等开源 WebGIS 库实现前端框架的搭建，采用axios进行前后端的交互操作，后端的实现采用Spring Boot技术整合MyBatis plus、Junit等常用技术外，还整合了GraphHopper和Jsprit导航规划包，使用Nominatim技术实现对地图上地点的反向查找，使用 PostgreSQL+PostGIS进行数据 存储，并在Ubuntu18.04上对OpenStreetMap服务器 的本地化进行了实现，并最后通过黑盒测试进行了软件各项指标的检 测，证明了本系统的正确性与可用性。

## 一些说明

- 由于GitHub上传文件大小限制，无法上传地图的空间数据，需要前往xxx网站下载china-latest.osm.pbf文件（很大，将近1G），并将其放入`/Users/bianzhenkun/IdeaProjects/graductionWork 2/src/main/resources/data`路径下，即可使用规划功能。
- 注意：第一次使用时会消耗较长时间，因为GraphHopper会将osm数据进行分析，产生节点、道路等静态数据，用于后期快速得到规划结果，具体为调用service.Impl.GHEngineRoutingServiceImpl.createGraphHopperInstance()进行初始化
- 由于登陆注册系统和对OSM地图底图更改的数据库布置在虚拟机上，因此目前无法使用，本仓库仅作为本人毕业设计的备份，因此并无大碍，直接根据router导航切换即可
- 由于开发阶段采用的是前后端分离，后端使用IDEA进行开发，前端使用WebStorm开发，同时上传时存在名称冲突，因此前端代码存储于web目录下

## 未来展望
- 可以融入更多的数据，同时使用多种评价标准进行规划时代价的计算，使规划更加准确
- 可以融入实时路况信息，同时实现动态调整路径规划结果，使规划结果更接近理想，不过需要设置最小调整间隔，否则会出现来回横跳的情况
- 部署考虑更多的并发访问情况，以及在保证性能的情况下提高安全保密性
