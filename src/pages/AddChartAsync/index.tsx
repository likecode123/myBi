
import { genChartByAiAsyncMqUsingPost, genChartByAiUsingPost  } from '@/services/mybi/chartController';

import React, { useState } from 'react';

import TextArea from 'antd/es/input/TextArea';
import { UploadOutlined } from '@ant-design/icons';
import {Button,message,Form,Select,Space,Input,Upload,Card} from 'antd';
import ReactECharts from 'echarts-for-react';
import { useForm } from 'antd/es/form/Form';
import { genChartByAiAsyncUsingPost } from '@/services/mybi/chartController';


const AddChart: React.FC = () => {
const[form] = useForm();
const [submitting, setSubmitting] = useState<boolean>(false);


  const onFinish = async (values: any) => {
    //避免重复提交
    if(submitting) {
      return;
    }
    setSubmitting(true);

    // 对接后端，上传数据
    const params = {
      ...values,
      file: undefined,
    };
    try {
      // 需要取到上传的原始数据file→file→originFileObj(原始数据)
      const res = await genChartByAiAsyncMqUsingPost(params, {}, values.file.file.originFileObj);
      // const res = await genChartByAiAsyncUsingPost(params, {}, values.file.file.originFileObj);
      // 正常情况下，如果没有返回值就分析失败，有，就分析成功
      if (!res?.data) {
        message.error('分析失败');
      } else {
        message.success('分析成功,内容稍后在我的图标页面查看');  
        form.resetFields();

      }  
    // 异常情况下，提示分析失败+具体失败原因
    } catch (e: any) {
      message.error('分析失败,' + e.message);
    }
    setSubmitting(false);
  }; 


  return (
<div className='add-chart-async'>
    <Card title="智能分析">
      <Form name="addChart" labelAlign="left" labelCol={{ span: 4 }}
            wrapperCol={{ span: 16 }} onFinish={onFinish} initialValues={{}}>
        <Form.Item
          name="goal"
          label="分析目标"
          rules={[{ required: true, message: '请输入分析目标' }]}
        >
          <TextArea placeholder="请输入你的分析需求，比如：分析网站用户的增长情况" />
        </Form.Item>
        <Form.Item name="name" label="图表名称">
          <Input placeholder="请输入图表名称" />
        </Form.Item>
        <Form.Item name="chartType" label="图表类型">
          <Select
            options={[
              { value: '折线图', label: '折线图' },
              { value: '柱状图', label: '柱状图' },
              { value: '堆叠图', label: '堆叠图' },
              { value: '饼图', label: '饼图' },
              { value: '雷达图', label: '雷达图' },
            ]}
          />
        </Form.Item>
        <Form.Item name="file" label="原始数据">
          <Upload name="file" maxCount={1}>
            <Button icon={<UploadOutlined />}>上传 CSV 文件</Button>
          </Upload>
        </Form.Item>

        <Form.Item wrapperCol={{ span: 16, offset: 4 }}>
          <Space>
            <Button type="primary" htmlType="submit" loading={submitting} disabled={submitting}>
              提交
            </Button>
            <Button htmlType="reset">重置</Button>
          </Space>
        </Form.Item>
      </Form>
    </Card>
</div>
  );
};
export default AddChart;
