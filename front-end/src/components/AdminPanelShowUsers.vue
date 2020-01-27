 <template>
 
 <table :key="variable"

            class="table b-table table-sm table-bordered">
            <thead class="thead-light">
              <tr>  <AdminPanelEditUserRole :user="user" :closeMethod="handleClose"  :key="EditRoleReRender" :methodForceUpdate="methodForceUpdate" v-if="this.editRole" /></tr>
              <tr>
                <th>firstName</th>
                <th>lastName</th>
                <th>email</th>
                <th>role</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in this.mainTable" v-bind:key="item.email">
                
                <td>{{ item.firstName }}</td>
                <td>{{ item.lastName }}</td>
                <td>{{ item.email }}</td>
                <td>{{ item.role }}</td>
                
                <td>
                  <b-button v-on:click="handleEditRoleButton(item)" pill variant="warning" size="sm" >Edit Role</b-button>
                </td>
              </tr>
            </tbody>
          </table>
</template>
<script>


import UserService from '../services/user.service.js';
import AdminPanelEditUserRole from './AdminPanelEditUserRole.vue';



export default {
    components:{
    AdminPanelEditUserRole
    },
  name: "AdminPanelShowUsers",
  props:{
    forceUpdate:Function  
  },
  data() {
    return {
        variable:0,
        editRole:false,
        user:null,
      mainTable: [],
      EditRoleReRender:true
    };
  },
    methods:{
        handleEditRoleButton :function(item)
        {
             this.user = item
            if(!(this.$store.state.auth.user.email == this.user.email))
            {
            this.editRole = true;
            }
              this.EditRoleReRender = !this.EditRoleReRender
        },
        handleClose:function(){
            this.editRole = false;
        },
        methodForceUpdate :function(){
            this.forceUpdate();
        }
    },
 mounted() {
        UserService.getUsers().then(array=>{this.mainTable = array})
}
}
</script>