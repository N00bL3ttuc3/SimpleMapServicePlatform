# SimpleMapServicePlatform

本仓库是本人本科毕业设计的记录，也是突发感慨，来记录一下

- 由于GitHub上传文件大小限制，无法上传地图的空间数据，需要前往xxx网站下载china-latest.osm.pbf文件（很大，将近1G），并将其放入`/Users/bianzhenkun/IdeaProjects/graductionWork 2/src/main/resources/data`路径下，即可使用规划功能。
- 注意：第一次使用时会消耗较长时间，因为GraphHopper会将osm数据进行分析，产生节点、道路等静态数据，用于后期快速得到规划结果，具体为调用service.Impl.GHEngineRoutingServiceImpl.createGraphHopperInstance()进行初始化
- 由于登陆注册系统和对OSM地图底图更改的数据库布置在虚拟机上，因此目前无法使用，本仓库仅作为本人毕业设计的备份，因此并无大碍，直接根据router导航切换即可
- 由于开发阶段采用的是前后端分离，后端使用IDEA进行开发，前端使用WebStorm开发，同时上传时存在名称冲突，因此前端代码存储于web目录下

