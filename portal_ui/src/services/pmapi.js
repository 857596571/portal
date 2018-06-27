import { stringify } from 'qs';
import request from '../utils/request';



export async function mdfPwd(params) {
    return request('/api/mdfPwd', {
        method: 'POST',
        body: params,
    }); 
}

export async function queryProjectNotice() {
    return request('/api/advertisement/getList');
}


export async function queryAdvertisements(params) {
    return request(`/api/advertisement/getList?${stringify(params)}`);
}
export async function addAdvertisements(params) {
    return request('/api/advertisement/add', {
        method: 'POST',
        body: params,
    }); 
}
export async function removeAdvertisements(params) {
    return request('/api/advertisement/deletes', {
        method: 'POST',
        body: params,
    });
}

export async function sortDown(params) {
    return request(`/api/advertisement/sortDown?${stringify(params)}`,{
        method:'POST',
    });
}
export async function sortUp(params) {
    return request(`/api/advertisement/sortUp?${stringify(params)}`,{
        method:'POST',
    });
}
export async function sortFirst(params) {
    return request(`/api/advertisement/sortFirst?${stringify(params)}`,{
        method:'POST',
    });
}


export async function queryMainbusiness(params) {
    return request(`/api/mainbusiness/getList?${stringify(params)}`);
}
export async function addMainbusiness(params) {
    return request('/api/mainbusiness/add', {
        method: 'POST',
        body: params,
    }); 
}
export async function removeMainbusiness(params) {
    return request('/api/mainbusiness/deletes', {
        method: 'POST',
        body: params,
    });
}

export async function sortDownMainbusiness(params) {
    return request(`/api/mainbusiness/sortDown?${stringify(params)}`,{
        method:'POST',
    });
}
export async function sortUpMainbusiness(params) {
    return request(`/api/mainbusiness/sortUp?${stringify(params)}`,{
        method:'POST',
    });
}
export async function sortFirstMainbusiness(params) {
    return request(`/api/mainbusiness/sortFirst?${stringify(params)}`,{
        method:'POST',
    });
}

export async function getMainbusiness(params) {
    return request(`/api/mainbusiness/search`,{
        method:'POST',
    });
}






export async function queryDynamic(params) {
    return request(`/api/dynamic/getList?${stringify(params)}`);
}
export async function addDynamic(params) {
    return request('/api/dynamic/add', {
        method: 'POST',
        body: params,
    }); 
}
export async function removeDynamic(params) {
    return request('/api/dynamic/deletes', {
        method: 'POST',
        body: params,
    });
}

export async function sortDownDynamic(params) {
    return request(`/api/dynamic/sortDown?${stringify(params)}`,{
        method:'POST',
    });
}
export async function sortUpDynamic(params) {
    return request(`/api/dynamic/sortUp?${stringify(params)}`,{
        method:'POST',
    });
}
export async function sortFirstDynamic(params) {
    return request(`/api/dynamic/sortFirst?${stringify(params)}`,{
        method:'POST',
    });
}



export async function queryPerson(params) {
    return request(`/api/person/getList?${stringify(params)}`);
}
export async function addPerson(params) {
    return request('/api/person/add', {
        method: 'POST',
        body: params,
    }); 
}
export async function removePerson(params) {
    return request('/api/person/deletes', {
        method: 'POST',
        body: params,
    });
}

export async function sortDownPerson(params) {
    return request(`/api/person/sortDown?${stringify(params)}`,{
        method:'POST',
    });
}
export async function sortUpPerson(params) {
    return request(`/api/person/sortUp?${stringify(params)}`,{
        method:'POST',
    });
}
export async function sortFirstPerson(params) {
    return request(`/api/person/sortFirst?${stringify(params)}`,{
        method:'POST',
    });
}


export async function queryPartner(params) {
    return request(`/api/partner/getList?${stringify(params)}`);
}
export async function addPartner(params) {
    return request('/api/partner/add', {
        method: 'POST',
        body: params,
    }); 
}
export async function removePartner(params) {
    return request('/api/partner/deletes', {
        method: 'POST',
        body: params,
    });
}

export async function sortDownPartner(params) {
    return request(`/api/partner/sortDown?${stringify(params)}`,{
        method:'POST',
    });
}
export async function sortUpPartner(params) {
    return request(`/api/partner/sortUp?${stringify(params)}`,{
        method:'POST',
    });
}
export async function sortFirstPartner(params) {
    return request(`/api/partner/sortFirst?${stringify(params)}`,{
        method:'POST',
    });
}
