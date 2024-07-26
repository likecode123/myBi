
import { listMyChartByPageUsingPost } from '@/services/mybi/chartController';
import React,{useState,useEffect} from 'react';
import { Avatar, List, } from 'antd';
import ReactECharts from 'echarts-for-react';
import Search from "antd/es/input/Search";
import {Card, message } from 'antd';
import { useModel } from '@@/exports';
const MyChartPage: React.FC = () => {
//分离初始条件
  const initSearchParams = {
    current: 1,
    pageSize: 3,
    sortField: 'createTime',
    sortOrder: 'desc',
}
// 定义变量存储图表数据
const [searchParams, setSearchParams] = useState<API.ChartQueryRequest>({ ...initSearchParams });
const { initialState } = useModel('@@initialState');
const { currentUser } = initialState ?? {};
const [chartList, setChartList] = useState<API.Chart[]>();
const [total, setTotal] = useState<number>(0);
const [loading, setLoading] = useState<boolean>(true);
/* 
    定义了一个状态(searchParams)和它对应的更新函数(setSearchParams)，并初始化为initSearchParams;
    searchParams是我们要发送给后端的查询条件，它的参数类型是API.ChartQueryRequest;
     {...} 是展开语法，它将 initSearchParams 中的所有属性展开并复制到一个新对象中，而不改变原始对象,因此可以避免在现有对象上直接更改值的对象变异操作。
     因为在 React 中，不推荐直接修改状态或属性，而是创建一个新对象并将其分配给状态或属性，这个方法就非常有用。
  */
     
     // 定义一个获取数据的异步函数
     const loadData = async () => {
      setLoading(true);
      try {
         const res =    await listMyChartByPageUsingPost(searchParams);
         if(res.data){
          // 如果成功,把图表数据回显到前端;如果为空，传一个空数组
        // 这里返回的是分页，res.data.records拿到数据列表
        setChartList(res.data.records ?? []);
        // 数据总数如果为空就返回01719189991361474562_0.9985805228590674
        setTotal(res.data.total ?? 0);
        // 隐藏图表的 title
        if (res.data.records) {
          res.data.records.forEach(data => {
            if (data.status === 'succeed') {
              const chartOption = JSON.parse(data.genChart ?? '{}');
              chartOption.title = undefined;
              data.genChart = JSON.stringify(chartOption);
            }
          })
        }
         }else{
        message.error('获取我的图表失败');
      }
      }catch (e:any) {
         console.error('获取我的图表失败', e.message);
      }
            
      setLoading(false);

     };

useEffect(() => {
// 这个页面首次渲染的时候，以及这个数组中的搜索条件发生变化的时候，会执行loadData方法,自动触发重新搜索
loadData();
},[searchParams]);


   
  return (
<div className='my-chart-page'>

{/* {JSON.stringify(chartList) } */}
{/* <br/>
      总数：{total} */}
<div>
        <Search placeholder="请输入图表名称" enterButton loading={loading} onSearch={(value) => {
          // 设置搜索条件
          setSearchParams({
            ...initSearchParams,
            name: value,
          })
        }}/>
      </div>


<div className='margin-16' />


      <List
        grid={{
          gutter: 16,
          xs: 1,
          sm: 1,
          md: 1,
          lg: 2,
          xl: 2,
          xxl: 2,
        }}

    itemLayout="vertical"
    size="large"
    pagination={{
      onChange: (page, pageSize) => {
        setSearchParams({
          ...searchParams,
          current: page,
          pageSize,
        })
      },
      current: searchParams.current,
          pageSize: searchParams.pageSize,
          total: total,
        }}
        loading={loading}
    dataSource={chartList}
    // footer={
    //   <div>
    //     <b>ant design</b> footer part
    //   </div>
    // }
    renderItem={(item) => (
      <List.Item key={item.id}>
      <Card style={{ width: '100%' }}>
        <List.Item.Meta
          avatar={<Avatar src={currentUser && currentUser.userAvatar} />}
          title={item.name}
          description={item.chartType ? '图表类型：' + item.chartType : undefined}
        />

              <div style={{ marginBottom: 16 }} />
              <p>{'分析目标：' + item.goal}</p>
        
              <div style={{ marginBottom: 16 }} />
              <ReactECharts option={item.genChart && JSON.parse(item.genChart)} />
      </Card>
    </List.Item>
   
    )}
  />
  总数：{total}
</div>
  );
};
export default MyChartPage;
