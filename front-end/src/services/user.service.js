import axios from 'axios';
import authHeader from './auth-header';
import config from '../config.js'
const API_URL = config.root;


class UserService {

  async getUserPrivileges() {
    return axios.get(API_URL + '/getUserPrivileges', {headers: authHeader()});
  }
  async getRoles() {
    return await (await axios.get(API_URL +'/roles',{headers:authHeader()})).data._embedded.roles;
  }
  async getPrivileges(){
    return (await axios.get(API_URL + '/privileges',{headers:authHeader()})).data._embedded.privileges;
  }
  async getUsers(){
    let array=[]
    await axios.get(API_URL + '/getAllUsers',{headers:authHeader()}).then(response =>{
     
      for(let i =0;i<response.data.length;i++)
            {
 
              array.push({ 
                id:response.data[i][4],
                firstName:response.data[i][0],
                lastName:response.data[i][1],
                email:response.data[i][2],
                role:response.data[i][3]
              })
            }
            return array
    })
  
  return array;
  }
  async getUserPrivilegesByUserId(id){
    return axios.get(API_URL+'/users/'+id,{headers:authHeader()})
  }

  async updateUserRole(roleName, userID){

    return axios.post(API_URL+'/changeUserRole?roleName='+roleName+'&userID='+userID,{headers:{...authHeader(),
      ...{"Access-Control-Allow-Origin": "*",
    "Content-Type": "application/json"}}})
  }
  async updateRolePrivileges(privilegesArr,roleID){
    return axios.post(API_URL+'/changeRolePrivileges?privilegesArr='+privilegesArr+'&roleID='+roleID,{headers:authHeader()})
  }
  async getRolePrivileges(roleName){
    
    let array=[]
   
    await axios.get(API_URL +'/getPrivilegesByRole?name='+roleName,{headers:authHeader()}).then(response=>{
      
  for(let i =0;i<response.data.length;i++)
  {

    array.push({ 
      id:response.data[i][0],
      name:response.data[i][1]
    })
  }
  return array
    });
    return array;
  }
  async deleteRole(roleName){
    return await axios.delete(API_URL+'/deleteRole?roleName='+roleName,{headers:authHeader()})
  }
  async deleteUser(email){
    return await axios.delete(API_URL+"/deleteUserByEmail?email="+email,{headers:authHeader()})
  }
  async createRolebyName(name){
    return await axios.post(API_URL+"/createRoleByName?roleName="+name,{headers:{...authHeader(),
      ...{"Access-Control-Allow-Origin": "*",
    "Content-Type": "application/json"}}})
  }
  async getAdvertisement(){
   
       return await axios.get(API_URL +'/advertisement',{headers:authHeader()})
  }
}
export default new UserService();