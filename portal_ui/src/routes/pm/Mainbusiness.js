import React, { PureComponent, Fragment} from 'react';
import { connect } from 'dva';
import { Table, Alert,Switch,Popconfirm, Badge, Divider,Row, Col, Card, Form, Input, Select,Upload, Icon, Button, Dropdown, Menu, InputNumber, DatePicker, Modal, message } from 'antd';
import StandardTable from '../../components/StandardTable';
import PageHeaderLayout from '../../layouts/PageHeaderLayout';
import moment from 'moment';


import styles from './Mainbusiness.less';

const FormItem = Form.Item;
const { Option } = Select;
const { TextArea } = Input;
const getValue = obj => Object.keys(obj).map(key => obj[key]).join(',');
const statusMap = ['default', 'success'];
const status = ['停用', '启用',];




const CreateForm = Form.create({
  mapPropsToFields(props) {
    return {
      id: Form.createFormField({value: props.mapPropsToFields.id}),
      num: Form.createFormField({value: props.mapPropsToFields.num}),
      name: Form.createFormField({value: props.mapPropsToFields.name}),
      url: Form.createFormField({value: props.mapPropsToFields.url}),
      status: Form.createFormField({value: !!+props.mapPropsToFields.status}),
      imgUrl: Form.createFormField({value: props.mapPropsToFields.imgUrl}),
      pid: Form.createFormField({value: props.mapPropsToFields.pid}),
      desce: Form.createFormField({value: props.mapPropsToFields.desce}),
    };
  },
})((props) => {
  const { modalVisible, form, handleAdd, handleModalVisible,mapPropsToFields,addOrEdit,pList,onRemoveImg,handleChange,fileList } = props;
  const okHandle = () => {
    form.validateFields((err, fieldsValue) => {
      if (err) return;
      if(typeof fieldsValue.imgUrl != 'string' && !fieldsValue.imgUrl.file.response.data){
        message.error(`请上传正确图片`);
        return false;
      }
      if(typeof fieldsValue.imgUrl != 'string' && fieldsValue.imgUrl.fileList.length>1){
        message.error(`只能上传一张图片`);
        return false;
      }
      handleAdd(fieldsValue);
    });
  };
  const props2 = !mapPropsToFields.imgUrl || addOrEdit? {
    action: '/api/mainbusiness/upload',
    listType: 'picture',
    className: 'upload-list-inline',
    fileList:fileList,
    onChange:({ fileList }) => {
      handleChange({fileList,fieldsValues:form.getFieldsValue()})
    },
  }:{
    action: '/api/mainbusiness/upload',
    listType: 'picture',
    className: 'upload-list-inline',
    onRemove:onRemoveImg,
    fileList:fileList,
    onChange:({ fileList }) => {
      handleChange({fileList,fieldsValues:form.getFieldsValue()})
    },
  };
  const children = [<Option key={0}>顶层主营业务</Option>];
    for (let i = 0; i < (pList ?pList.length :0); i++) {
      children.push(<Option key={pList[i].id}>{pList[i].name}</Option>);
    }
  
  return (
    <Modal
      title={addOrEdit ? '添加主营业务内容':'修改主营业务内容'}
      visible={modalVisible}
      onOk={addOrEdit ? okHandle : okHandle}
      onCancel={() => {onRemoveImg();handleModalVisible()}}
    >
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="id" 
        className={styles.hide}
      >
        {form.getFieldDecorator('id', {
          rules: [],
        })(
          <Input placeholder="" className={styles.hide}/>
        )}
      </FormItem>
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="num" 
        className={styles.hide}
      >
        {form.getFieldDecorator('num', {
          rules: [],
        })(
          <Input placeholder="" className={styles.hide}/>
        )}
      </FormItem>
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="标题"
      >
        {form.getFieldDecorator('name', {
          rules: [{ required: true, message: '必填字段' },{max:50,message:'最大长度不能超过50字符'}],
        })(
          <Input placeholder="请输入" />
        )}
      </FormItem>
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="上级业务名称"
      >
        {form.getFieldDecorator('pid', {
          rules: [],
        })(
          <Select
            style={{ width: '100%' }}
            onChange={()=>{}}
          >
            {children}
          </Select>
        )}
      </FormItem>
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="链接"
      >
        {form.getFieldDecorator('url', {
          rules: [{type:'url',message:'必须输入网址'},{max:200,message:'最大长度不能超过200字符'}],
        })(
          <Input placeholder="请输入" />
        )}
      </FormItem>
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="状态"
      >
        {form.getFieldDecorator('status', {
          valuePropName: 'checked' ,
        })(
          <Switch checkedChildren="启用"  unCheckedChildren="停用" />
        )}
      </FormItem>
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="图片"
      >
        {form.getFieldDecorator('imgUrl', {
          rules: [{ required: true, message: '请上传图片' }],
        })(
          <Upload { ...props2 }>
            <i style={{fontSize:'12px',color:'red'}}>建议图片比例为父级4：3(180px*135px)、子级1：1(100px*100px)</i><br/>
            <Button>
              <Icon type="upload" /> upload
            </Button>
          </Upload>
        )}
      </FormItem>
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="描述"
      >
        {form.getFieldDecorator('desce', {
          rules: [{max:500,message:'最大长度不能超过500字符'}],
        })(
          <TextArea rows={4} />
        )}
      </FormItem>
    </Modal>
  );
});

@connect(({ mainbusiness, loading }) => ({
  mainbusiness,
  loading: loading.models.mainbusiness,
}))
@Form.create()
export default class Mainbusiness extends PureComponent {
  state = {
    modalVisible: false,
    expandForm: false,
    selectedRows: [],
    formValues: {},
    mapPropsToFields:{},
    addOrEdit:true,
    fileList:[],
  };
  handleChange = ({ fileList ,fieldsValues }) => {
    debugger;
    if(fieldsValues){
      this.setState({ fileList:fileList.length>1?[fileList.reverse()[0]]:fileList ,mapPropsToFields:fieldsValues})
    }else{
      this.setState({ fileList:fileList.length>1?[fileList.reverse()[0]]:fileList})
    }
  }
  columns = [
    
    {
      title: '标题',
      dataIndex: 'name',
      render(val) {
        return val.length>20 ? val.substring(0,20)+'...':val;
      },
    },
    {
      title: '上级业务名称',
      dataIndex: 'uname',
      render(val) {
        return val.length>20 ? val.substring(0,20)+'...':val;
      },
    },
    {
      title: '链接',
      dataIndex: 'url',
      render(val) {
        return val.length>20 ? val.substring(0,20)+'...':val;
      },
    },
    {
      title: '描述',
      dataIndex: 'desce',
      render(val) {
        return val.length>20 ? val.substring(0,20)+'...':val;
      },
    },
    {
      title: '状态',
      dataIndex: 'status',
      filters: [
        {
          text: status[0],
          value: false,
        },
        {
          text: status[1],
          value: true,
        }
      ],
      render(val) {
        return <Badge status={statusMap[val]} text={status[val]} />;
      },
    },
    {
      title: '操作',
      render: (row) => (
        <Fragment>
          <a href="javascript:void(0);" onClick={(arg) => {this.edit( row); }} >编辑</a>
          <Divider type="vertical" />
          <a href="javascript:void(0);" onClick={(arg) => {this.sortNum(arg, '3', row); }}>置顶</a>
          <Divider type="vertical" />
          <a href="javascript:void(0);" onClick={(arg) => {this.sortNum(arg, '0', row); }}><Icon type="arrow-up" /></a>
          <Divider type="vertical" />
          <a href="javascript:void(0);" onClick={(arg) => {this.sortNum(arg, '1', row); }}><Icon type="arrow-down" /></a>
        </Fragment>
      ),
    },
    {
      title: '序号',
      dataIndex: 'id',
      key:'id',
      className:styles.hide
    }, 
    {
      title: 'pid',
      dataIndex: 'pid',
      key:'pid',
      className:styles.hide
    }, 
  ]

  sortNum = (e, key, row) =>{
    const {dispatch } = this.props;
    let type = 'mainbusiness/sortDown';
    if(key == 0){
      type = 'mainbusiness/sortUp';
    }
    if(key == 3){
      type = 'mainbusiness/sortFirst';
    }
    dispatch({
      type: type,
      payload:{id:row.id},
      callback: () => {
        dispatch({
          type: 'mainbusiness/fetch',
        });
      },
    });
    console.log( key, row);
  }

  edit = (row) =>{
    let fileList = row.imgUrl && [{
      uid:-1,
      name:row.imgUrl.split('/').slice(-1)[0],
      status:'done',
      url:row.imgUrl,
      thumbUrl:row.imgUrl
    }];
    this.setState({
      mapPropsToFields:row,
      addOrEdit:false,
      fileList
    });
    this.handleModalVisible(true);
    
  }
  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({
      type: 'mainbusiness/fetch',
    });
  }

  handleStandardTableChange = (pagination, filtersArg, sorter) => {
    const { dispatch } = this.props;
    const { formValues } = this.state;

    const filters = Object.keys(filtersArg).reduce((obj, key) => {
      const newObj = { ...obj };
      newObj[key] = getValue(filtersArg[key]);
      return newObj;
    }, {});

    const params = {
      currentPage: pagination.current,
      pageSize: pagination.pageSize,
      ...formValues,
      ...filters,
    };
    if (sorter.field) {
      params.sorter = `${sorter.field}_${sorter.order}`;
    }

    dispatch({
      type: 'mainbusiness/fetch',
      payload: params,
    });
  }

  handleFormReset = () => {
    const { form, dispatch } = this.props;
    form.resetFields();
    this.setState({
      formValues: {},
    });
    dispatch({
      type: 'mainbusiness/fetch',
      payload: {},
    });
  }

  toggleForm = () => {
    this.setState({
      expandForm: !this.state.expandForm,
    });
  }

  handleMenuClick = (e) => {
    const { dispatch } = this.props;
    const { selectedRows } = this.state;

    if (!selectedRows) return;

    switch (e) {
      case 'remove':
        dispatch({
          type: 'mainbusiness/remove',
          payload:selectedRows.map(row => row.id),
          callback: () => {
            this.setState({
              selectedRows: [],
            });
            dispatch({
              type: 'mainbusiness/fetch',
            });
          },
        });
        break;
      default:
        break;
    }
  }

  handleSelectRows = (rows) => {
    this.setState({
      selectedRows: rows,
    });
  }

  handleSearch = (e) => {
    e.preventDefault();

    const { dispatch, form } = this.props;

    form.validateFields((err, fieldsValue) => {
      if (err) return;

      const values = {
        ...fieldsValue,
        updatedAt: fieldsValue.updatedAt && fieldsValue.updatedAt.valueOf(),
      };

      this.setState({
        formValues: values,
      });

      dispatch({
        type: 'mainbusiness/fetch',
        payload: values,
      });
    });
  }

  handleModalVisible = (flag) => {
    this.setState({
      modalVisible: !!flag,
    });
    
  }
  onRemoveImg = () => {
    let mapPropsToFields = 
    this.setState({
      mapPropsToFields:{
        ...this.state.mapPropsToFields,
        imgUrl:undefined
      },
      fileList:[],
      addOrEdit:true
    })
  }
  handleAdd = (fields) => {
    const {dispatch} = this.props;
    dispatch({
      type: 'mainbusiness/add',
      payload:fields,
      callback: () => {
        message.success('添加成功');
        this.setState({
          modalVisible: false,
          fileList:[]
        });
        dispatch({
          type: 'mainbusiness/fetch',
        });
      },
    });

    
  }

  renderSimpleForm() {
    const { getFieldDecorator } = this.props.form;
    return (
      <Form onSubmit={this.handleSearch} layout="inline">
        <Row gutter={{ md: 8, lg: 24, xl: 48 }}>
          <Col md={8} sm={24}>
            <FormItem label="标题">
              {getFieldDecorator('name')(
                <Input placeholder="请输入" />
              )}
            </FormItem>
          </Col>
          <Col md={8} sm={24}>
            
          </Col>
          <Col md={8} sm={24}>
            <span className={styles.submitButtons}>
              <Button type="primary" htmlType="submit">查询</Button>
              <Button style={{ marginLeft: 8 }} onClick={this.handleFormReset}>重置</Button>
              
            </span>
          </Col>
        </Row>
      </Form>
    );
  }


  

  render() {
    const { mainbusiness: { data,pList }, loading } = this.props;
    const { selectedRows, modalVisible,mapPropsToFields,addOrEdit,fileList } = this.state;

    const menu = (
      <Menu onClick={this.handleMenuClick} selectedKeys={[]}>
        <Menu.Item key="remove">删除</Menu.Item>
      </Menu>
    );

    const parentMethods = {
      handleAdd: this.handleAdd,
      handleModalVisible: this.handleModalVisible,
      onRemoveImg:this.onRemoveImg,
      handleChange:this.handleChange
    };
    const columns1 = [{
      title: 'Name',
      dataIndex: 'name',
      key: 'name',
    }, {
      title: 'Age',
      dataIndex: 'num',
      key: 'age',
      width: '12%',
    }, {
      title: 'Address',
      dataIndex: 'uname',
      width: '30%',
      key: 'address',
    }];
    
    
    
    return (
      <PageHeaderLayout title="查询表格">
        <Card bordered={false}>
          <div className={styles.tableList}>
            <div className={styles.tableListForm}>
              {this.renderSimpleForm()}
            </div>
            <div className={styles.tableListOperator}>
              <Button icon="plus" type="primary" onClick={() => { this.setState({mapPropsToFields:{}});this.handleModalVisible(true)}}>
                添加
              </Button>
              
              {
                selectedRows.length > 0 && (
                  <Popconfirm title="确认删除？" okText="确认" cancelText="取消"  onConfirm={() => this.handleMenuClick('remove')}>
                    <Button icon="delete">
                      删除
                    </Button>
                  </Popconfirm>
                )
              }
            </div>
            <StandardTable
              selectedRows={selectedRows}
              loading={loading}
              columns={this.columns}
              data={data}
              onSelectRow={this.handleSelectRows}
              onChange={this.handleStandardTableChange}
            /> 
          </div>
        </Card>
        {modalVisible&&<CreateForm
          {...parentMethods}
          modalVisible={modalVisible}
          addOrEdit={addOrEdit}
          pList = {pList}
          fileList = {fileList}
          mapPropsToFields = {mapPropsToFields}
        />}
        
      </PageHeaderLayout>
    );
  }
}
