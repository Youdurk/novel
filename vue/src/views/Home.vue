<template>
      <div>
    <el-row :gutter="20">
      <el-col :span="6" >
        <div>
          <el-statistic group-separator="," :precision="2" :value="shu[2]" :title="coun[0]"></el-statistic>
        </div>
      </el-col>
      <el-col :span="6">
        <div>
          <el-statistic :title="coun[1]">
            <template slot="formatter"> {{shu[1]}}</template>
          </el-statistic>
        </div>
      </el-col>
      <el-col :span="6">
        <div>
          <el-statistic group-separator="," :precision="2" decimal-separator="." :value="shu[0]" :title="coun[2]">
            <template slot="prefix">
              <i class="el-icon-s-flag" style="color: red"></i>
            </template>
            <template slot="suffix">
              <i class="el-icon-s-flag" style="color: blue"></i>
            </template>
          </el-statistic>
        </div>
      </el-col>
      <el-col :span="6">
        <div>
          <el-statistic :value="shu[3]" :title="coun[3]">
            <template slot="suffix">
              <span @click="like = !like" class="like">
                <i class="el-icon-star-on" style="color:red" v-show="!!like"></i>
                <i class="el-icon-star-off" v-show="!like"></i>
              </span>
            </template>
          </el-statistic>
        </div>
      </el-col>
    </el-row>
    <el-row>
        
    </el-row>
    <div>
        <div ref="chart" style="width:50%;height:376px;margin: 50px;"></div>
    </div>
  </div>
</template>
<script>
export default {
    name:'home',
    data() {
      return {
        like: true,
        nofu: '今年的增长',
        xAxi:[],
        coun:[],
        shu:[],
      }
    },
    mounted(){
        this.getEchartData()
    },
    created(){
      this.count()
    },
    methods:{
        getEchartData(){
            const chart = this.$refs.chart
        if (chart) {
          const myChart = this.$echarts.init(chart)
        //     const myecharts=this.$root.echarts.init(document.getElementById('chart'))
          let  option = {
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'cross',
      crossStyle: {
        color: '#999'
      }
    }
  },
  toolbox: {
    feature: {
      dataView: { show: true, readOnly: false },
      magicType: { show: true, type: ['line', 'bar'] },
      restore: { show: true },
      saveAsImage: { show: true }
    }
  },
  legend: {
    data: ['Evaporation', 'Precipitation', '用户总数']
  },
  xAxis: [
    {
      type: 'category',
      data: this.xAxi,
      axisPointer: {
        type: 'shadow'
      }
    }
  ],
  yAxis: [
    {
      type: 'value',
      name: 'Precipitation',
      min: 0,
      max: 80,
      interval: 10,
      axisLabel: {
        formatter: '{value} '
      }
    }
  ],
  series: [
    {
      name: 'Evaporation',
      type: 'bar',
      tooltip: {
        valueFormatter: function (value) {
          return value;
        }
      },
      data: []
    },
    {
      name: 'Precipitation',
      type: 'bar',
      tooltip: {
        valueFormatter: function (value) {
          return value ;
        }
      },
      data: [
        
      ]
    },
    {
      name: '用户总数',
      type: 'line',
      yAxisIndex: 0,
      tooltip: {
        valueFormatter: function (value) {
          return value ;
        }
      },
      data: []
    }
  ]
}
console.log(option.xAxis.data)
this.request.get("/user/").then(res=>{
  option.legend.data
  res.data.x.forEach(item => {
   this.xAxi.push(item)
  })
  res.data.y.forEach(ie=>{
   option.series[2].data.push(ie)
  })
myChart.setOption(option)
})
this.request.get("/user/au").then(res=>{
  option.legend.data[0]=res.data.name
option.series[0].name=res.data.name
res.data.y.forEach(ie=>{
 option.series[0].data.push(ie)
})
myChart.setOption(option)
})
this.request.get("/user/reu").then(res=>{
  option.legend.data[1]=res.data.name
option.series[1].name=res.data.name
res.data.y.forEach(ie=>{
 option.series[1].data.push(ie)
})
myChart.setOption(option)
})

        }
        },
        count(){
          this.request.get("/user/cout").then(res=>{
          this.coun=res.data.name
          this.shu=res.data.统计
          console.log(this.shu)
          })
        }
    }
}
</script>
<style>
.like {
    cursor: pointer;
    font-size: 25px;
    display: inline-block;
  }
</style>