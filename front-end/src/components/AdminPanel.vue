<template>
  <div>
    <b-row>
      <b-col cols="6" md="4">
        <div class="small-table">
          <table class="table b-table table-dark table-hover">
            <thead class="thead-dark">
              <tr>
                <th>Options Panel</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td @click="showUsers">Show Users</td>
              </tr>
              <tr>
                <td @click="showRoles">Show Roles</td>
              </tr>
              <tr>
                <td @click="showPrivileges">Show Privileges</td>
              </tr>
              <tr>
                <td @click="showOpinionsTable">Show Opinions</td>
              </tr>
              <tr>
                <td @click="showAddAdvPanel">Add Advertisement</td>
                </tr>
            </tbody>
          </table>
        </div>
      </b-col>
      <b-col cols="12" md="8">

         <AddAdvertisement v-if="this.changeMainTable ==='addAdvertisement'" />
    <OpinionsInAdminPanel  v-if="this.changeMainTable === 'opinions'"/>
    <AdminPanelShowRoles v-if="this.changeMainTable==='showRoles'"   :forceUpdate = "forceUpdateAdminPanelShowRoles"  :key="AdminPanelShowRolesReRender"/>
    <AdminPanelShowPrivileges v-if="this.changeMainTable==='showPrivileges'"/>
    <AdminPanelShowUsers v-if="this.changeMainTable==='showUsers'"  :forceUpdate = "forceUpdateAdminPanelShowUsers" :key="AdminPanelShowUsersReRender"/>
      </b-col>
  
    </b-row>
    
  
  </div>
</template>

<script>

import OpinionsInAdminPanel from "./OpinionsInAdminPanel";
import AddAdvertisement from './AddAdvertisement.vue';
import AdminPanelShowRoles from './AdminPanelShowRoles';
import AdminPanelShowPrivileges from './AdminPanelShowPrivileges';
import AdminPanelShowUsers from './AdminPanelShowUsers';
export default {
  name: "AdminPanel",
  components: {
    OpinionsInAdminPanel,
    AddAdvertisement,
    AdminPanelShowRoles,
    AdminPanelShowPrivileges,
    AdminPanelShowUsers
  },
  data() {
    return {
      AdminPanelShowUsersReRender:true,
      AdminPanelShowRolesReRender:true,
      changeMainTable: "empty",
      items: [{ option: "Show users" }, { option: "Show roles" }],
      mainTable: [],
    };
  },
  methods: {

    showOpinionsTable: function ()  {
      this.changeMainTable = "opinions";
    },
    showAddAdvPanel:function(){
      this.changeMainTable = 'addAdvertisement';
    },
    showRoles:function(){
      this.changeMainTable='showRoles';
    },
    showPrivileges:function(){
      this.changeMainTable='showPrivileges';
    },
      showUsers:function(){
      this.changeMainTable='showUsers';
    },
    forceUpdateAdminPanelShowUsers(){
      this.AdminPanelShowUsersReRender = !this.AdminPanelShowUsersReRender;
    },
     forceUpdateAdminPanelShowRoles(){
      this.AdminPanelShowRolesReRender = !this.AdminPanelShowRolesReRender;
    },
 
  }
};
</script>
