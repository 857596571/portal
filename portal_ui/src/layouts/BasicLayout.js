import React from 'react';
import PropTypes from 'prop-types';
import { Layout, Icon, message,Modal,Input,Form,notification  } from 'antd';
import DocumentTitle from 'react-document-title';
import { connect } from 'dva';
import { Route, Redirect, Switch, routerRedux } from 'dva/router';
import { ContainerQuery } from 'react-container-query';
import classNames from 'classnames';
import { enquireScreen } from 'enquire-js';
import GlobalHeader from '../components/GlobalHeader';
import GlobalFooter from '../components/GlobalFooter';
import SiderMenu from '../components/SiderMenu';
import NotFound from '../routes/Exception/404';
import { getRoutes } from '../utils/utils';
import Authorized from '../utils/Authorized';
import { getMenuData } from '../common/menu';
import logo from '../assets/logo.png';

const { Content } = Layout;
const { AuthorizedRoute } = Authorized;
const FormItem = Form.Item;

/**
 * 根据菜单取得重定向地址.
 */
const redirectData = [];
const getRedirect = (item) => {
  if (item && item.children) {
    if (item.children[0] && item.children[0].path) {
      redirectData.push({
        from: `/${item.path}`,
        to: `/${item.children[0].path}`,
      });
      item.children.forEach((children) => {
        getRedirect(children);
      });
    }
  }
};

const CreateForm = Form.create()((props) => {
  const { modalVisible, form, handleOk, handleModalVisible } = props;
  const okHandle = () => {
    form.validateFields((err, fieldsValue) => {
      console.log(err);
      if (err) {return;}
      console.log(fieldsValue.newPwd == fieldsValue.rePwd);
      console.log(fieldsValue.newPwd,fieldsValue.rePwd);
      if(fieldsValue.newPwd != fieldsValue.rePwd){
        notification.open({
          message: '输入错误',
          description: '两次密码输入不一致',
        });
        return;
      }
      handleOk(fieldsValue);
    });
  };
  
  
  
  return (
    <Modal
      title={'修改密码'}
      visible={modalVisible}
      onOk={okHandle}
      onCancel={() => handleModalVisible()}
    >
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="原密码"
      >
        {form.getFieldDecorator('pwd', {
          rules: [{ required: true, message: 'Please input some description...' }],
        })(
          <Input placeholder="请输入" />
        )}
      </FormItem>
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="新密码"
      >
        {form.getFieldDecorator('newPwd', {
          rules: [{ required: true, message: 'Please input some description...' }],
        })(
          <Input placeholder="请输入" />
        )}
      </FormItem>
      
      <FormItem
        labelCol={{ span: 5 }}
        wrapperCol={{ span: 15 }}
        label="确认密码"
      >
        {form.getFieldDecorator('rePwd', {
          rules: [{ required: true, message: 'Please input some description...' }],
        })(
          <Input placeholder="请输入" />
        )}
      </FormItem>
      
    </Modal>
  );
});

getMenuData().forEach(getRedirect);

const query = {
  'screen-xs': {
    maxWidth: 575,
  },
  'screen-sm': {
    minWidth: 576,
    maxWidth: 767,
  },
  'screen-md': {
    minWidth: 768,
    maxWidth: 991,
  },
  'screen-lg': {
    minWidth: 992,
    maxWidth: 1199,
  },
  'screen-xl': {
    minWidth: 1200,
  },
};

let isMobile;
enquireScreen((b) => {
  isMobile = b;
});

class BasicLayout extends React.PureComponent {
  static childContextTypes = {
    location: PropTypes.object,
    breadcrumbNameMap: PropTypes.object,
  }
  state = {
    isMobile,
    visible:false,
    timeEdit:false,
  };
  getChildContext() {
    const { location, routerData } = this.props;
    return {
      location,
      breadcrumbNameMap: routerData,
    };
  }
  componentDidMount() {
    enquireScreen((mobile) => {
      this.setState({
        isMobile: mobile,
      });
    });
    this.props.dispatch({
      type: 'user/fetchCurrent',
    });
  }
  getPageTitle() {
    const { routerData, location } = this.props;
    const { pathname } = location;
    let title = '宇任拓门户管理系统';
    if (routerData[pathname] && routerData[pathname].name) {
      title = `${routerData[pathname].name} - 宇任拓门户管理系统`;
    }
    return title;
  }
  getBashRedirect = () => {
    // According to the url parameter to redirect
    // 这里是重定向的,重定向到 url 的 redirect 参数所示地址
    const urlParams = new URL(window.location.href);

    const redirect = urlParams.searchParams.get('redirect');
    // Remove the parameters in the url
    if (redirect) {
      urlParams.searchParams.delete('redirect');
      window.history.replaceState(null, 'redirect', urlParams.href);
    } else {
      return '/advertisement';
    }
    return redirect;
  }
  handleMenuCollapse = (collapsed) => {
    this.props.dispatch({
      type: 'global/changeLayoutCollapsed',
      payload: collapsed,
    });
  }
  handleNoticeClear = (type) => {
    message.success(`清空了${type}`);
    this.props.dispatch({
      type: 'global/clearNotices',
      payload: type,
    });
  }
  handleMenuClick = ({ key }) => {
    if (key === 'updatePwd') {
      this.setState({
        visible: true,
      });
    }
    if (key === 'logout') {
      this.props.dispatch({
        type: 'login/logout',
      });
    }
  }
  handleNoticeVisibleChange = (visible) => {
    if (visible) {
      this.props.dispatch({
        type: 'global/fetchNotices',
      });
    }
  }
  handleOk = (fields) => {
    this.props.dispatch({
      type: 'login/mdfPwd',
      payload:fields,
      callback: (msg) => {
        if(msg.ok == 'false' || !msg.ok){
          notification.open({
            message: '输入错误',
            description: msg.message,
          });
        }else{
          message.success("修改成功");
          
          this.props.dispatch({
            type: 'user/fetchCurrent',
          });
          this.setState({
            visible: false,
          });
        }
      },
    });
    
  }
  handleCancel = (e) => {
    console.log(e);
    this.setState({
      visible: false,
    });
  }



  
  render() {
    const {
      currentUser, collapsed, fetchingNotices, notices, routerData, match, location,
    } = this.props;
    const time1 = 90*24*60*60*1000;
    const time2 = new Date().getTime();
    if(currentUser.updateDate){
      if(time2 - currentUser.updateDate > time1){
        this.setState({
          timeEdit: true,
        });
      }else{
        this.setState({
          timeEdit: false,
        });
      }
    }
    const bashRedirect = this.getBashRedirect();
    const layout = (
      <Layout>
        <SiderMenu
          logo={logo}
          // 不带Authorized参数的情况下如果没有权限,会强制跳到403界面
          // If you do not have the Authorized parameter
          // you will be forced to jump to the 403 interface without permission
          Authorized={Authorized}
          menuData={getMenuData()}
          collapsed={collapsed}
          location={location}
          isMobile={this.state.isMobile}
          onCollapse={this.handleMenuCollapse}
        />
        <Layout>
          <GlobalHeader
            logo={logo}
            currentUser={currentUser}
            fetchingNotices={fetchingNotices}
            notices={notices}
            collapsed={collapsed}
            isMobile={this.state.isMobile}
            onNoticeClear={this.handleNoticeClear}
            onCollapse={this.handleMenuCollapse}
            onMenuClick={this.handleMenuClick}
            onNoticeVisibleChange={this.handleNoticeVisibleChange}
          />
          <Content style={{ margin: '24px 24px 0', height: '100%' }}>
            <Switch>
              {
                redirectData.map(item =>
                  <Redirect key={item.from} exact from={item.from} to={item.to} />
                )
              }
              {
                getRoutes(match.path, routerData).map(item =>
                  (
                    <AuthorizedRoute
                      key={item.key}
                      path={item.path}
                      component={item.component}
                      exact={item.exact}
                      authority={item.authority}
                      redirectPath="/exception/403"
                    />
                  )
                )
              }
              
              <Redirect exact from="/" to={bashRedirect} />
              <Route render={NotFound} />
            </Switch>
          </Content>
          {/* <GlobalFooter
            links={[{
              key: 'Pro 首页',
              title: 'Pro 首页',
              href: 'http://pro.ant.design',
              blankTarget: true,
            }, {
              key: 'github',
              title: <Icon type="github" />,
              href: 'https://github.com/ant-design/ant-design-pro',
              blankTarget: true,
            }, {
              key: '宇任拓门户管理系统',
              title: '宇任拓门户管理系统',
              href: 'http://ant.design',
              blankTarget: true,
            }]}
            copyright={
              <div>
                Copyright <Icon type="copyright" /> 2018 蚂蚁金服体验技术部出品
              </div>
            }
          /> */}
        </Layout>
      </Layout>
    );

    return (
      <div>
      <DocumentTitle title={this.getPageTitle()}>
        <ContainerQuery query={query}>
          {params => <div className={classNames(params)}>{layout}</div>}
          
        </ContainerQuery>
      </DocumentTitle>
      
    <CreateForm
          handleOk={this.handleOk}
          handleModalVisible={this.handleCancel}
          modalVisible={this.state.visible || this.state.timeEdit}
        />
    </div>
    );
  }
}

export default connect(({ user, global, loading }) => ({
  currentUser: user.currentUser,
  collapsed: global.collapsed,
  fetchingNotices: loading.effects['global/fetchNotices'],
  notices: global.notices,
}))(BasicLayout);
