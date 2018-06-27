import React, { Component } from 'react';
import { Editor } from 'react-draft-wysiwyg';
import { Table, Alert,Switch, Badge, Divider,Row, Col, Card, Form, Input, Select,Upload, Icon, Button, Dropdown, Menu, InputNumber, DatePicker, Modal, message } from 'antd';
import 'react-draft-wysiwyg/dist/react-draft-wysiwyg.css';
import draftToHtml from 'draftjs-to-html';
import {  EditorState, convertToRaw, ContentState} from 'draft-js';
import htmlToDraft from 'html-to-draftjs';
const rawContentState = {"entityMap":{"0":{"type":"IMAGE","mutability":"MUTABLE","data":{"src":"http://i.imgur.com/aMtBIep.png","height":"auto","width":"100%"}}},"blocks":[{"key":"9unl6","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}},{"key":"95kn","text":" ","type":"atomic","depth":0,"inlineStyleRanges":[],"entityRanges":[{"offset":0,"length":1,"key":0}],"data":{}},{"key":"7rjes","text":"","type":"unstyled","depth":0,"inlineStyleRanges":[],"entityRanges":[],"data":{}}]};
const html = '<p>Hey this <strong>editor</strong> rocks 😀</p>';
export default class Wysiwyg extends Component {
    
    state = {
        editorContent: undefined,
        contentState: rawContentState,
        editorState: EditorState.createWithContent(ContentState.createFromBlockArray(htmlToDraft(this.props.context).contentBlocks)),
    };

    onEditorChange = (editorContent) => {
        this.setState({
            editorContent,
        });
    };

    clearContent = () => {
        this.setState({
            contentState: '',
        });
    };

    onContentStateChange = (contentState) => {
        console.log('contentState', contentState);
    };

    onEditorStateChange = (editorState) => {
        this.setState({
            editorState,
        });
    };

    imageUploadCallBack = file => new Promise(
        (resolve, reject) => {
            const xhr = new XMLHttpRequest(); // eslint-disable-line no-undef
            xhr.open('POST', '/api/dynamic/upload');
            xhr.setRequestHeader('Authorization', 'Client-ID 8d26ccd12712fca');
            const data = new FormData(); // eslint-disable-line no-undef
            data.append('file', file);
            xhr.send(data);
            xhr.addEventListener('load', () => {
                const response = JSON.parse(xhr.responseText);
                resolve({"data":{"link":response.data}});
            });
            xhr.addEventListener('error', () => {
                const error = JSON.parse(xhr.responseText);
                reject(error);
            });
        }
    );

    render() {
        const { editorContent, editorState,contentState } = this.state;
        
        return (
            <div className="gutter-example button-demo">
                {/* <BreadcrumbCustom first="UI" second="富文本" /> draftToHtml(editorContent, null, 4)*/}
                <Row gutter={16}>
                    <Col className="gutter-row" md={24}>
                        <div className="gutter-box">
                            <Card title="富文本编辑器" bordered={false} extra={<a href="javascript:void(0)" onClick={()=>{this.props.save(draftToHtml(convertToRaw(editorState.getCurrentContent())));console.log(editorContent)}}>保存</a>}>
                                <Editor
                                    editorState={editorState}
                                    toolbarClassName="home-toolbar"
                                    wrapperClassName="home-wrapper"
                                    editorClassName="home-editor"
                                    onEditorStateChange={this.onEditorStateChange}
                                    toolbar={{
                                        history: { inDropdown: true },
                                        inline: { inDropdown: false },
                                        list: { inDropdown: true },
                                        textAlign: { inDropdown: true },
                                        image: { uploadCallback: this.imageUploadCallBack },
                                    }}
                                    onContentStateChange={this.onEditorChange}
                                    placeholder= "请输入正文~~尝试@哦，有惊喜"
                                    spellCheck
                                    onFocus={() => {console.log('focus')}}
                                    onBlur={() => {console.log('blur')}}
                                    onTab={() => {console.log('tab'); return true;}}
                                    localization={{ locale: 'zh', translations: {'generic.add': 'Add'} }}
                                    mention={{
                                        separator: ' ',
                                        trigger: '@',
                                        caseSensitive: true,
                                        suggestions: [
                                            { text: 'A', value: 'AB', url: 'href-a' },
                                            { text: 'AB', value: 'ABC', url: 'href-ab' },
                                            { text: 'ABC', value: 'ABCD', url: 'href-abc' },
                                            { text: 'ABCD', value: 'ABCDDDD', url: 'href-abcd' },
                                            { text: 'ABCDE', value: 'ABCDE', url: 'href-abcde' },
                                            { text: 'ABCDEF', value: 'ABCDEF', url: 'href-abcdef' },
                                            { text: 'ABCDEFG', value: 'ABCDEFG', url: 'href-abcdefg' },
                                        ],
                                    }}
                                />

                                <style>{`
                                    .home-editor {
                                        min-height: 300px;
                                    }
                                `}</style>
                            </Card>
                        </div>
                    </Col>
                    {/* <Col className="gutter-row" md={8}>
                        <Card title="同步转换HTML" bordered={false}>
                            <pre>{draftToHtml(editorContent)}</pre>
                        </Card>
                    </Col>
                    <Col className="gutter-row" md={8}>
                        <Card title="同步转换MarkDown" bordered={false}>
                            <pre style={{whiteSpace: 'pre-wrap'}}>{draftToMarkdown(editorContent)}</pre>
                        </Card>
                    </Col>
                    <Col className="gutter-row" md={8}>
                        <Card title="同步转换JSON" bordered={false}>
                            <pre style={{whiteSpace: 'normal'}}>{JSON.stringify(editorContent)}</pre>
                        </Card>
                    </Col> */}
                </Row>
            </div>
        );
    }
}