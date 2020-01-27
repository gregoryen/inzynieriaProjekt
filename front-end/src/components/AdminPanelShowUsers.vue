 <template>
 <div>

   <table>
   <td><label>First Name</label>
   <input type="text" v-model="firstNameInput" placeHolder="First Name"/>
    
    </td><td><label>Last Name</label>
   <input type="text" v-model="lastNameInput" placeHolder="Last Name"/>
   </td><td><label>Role Name</label>
   <input type="text" v-model="roleNameInput" placeHolder="Name"/></td>
 </table>
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
              <tr v-for="item in filteredUsers()" v-bind:key="item.email">
                
                <td>{{ item.firstName }}</td>
                <td>{{ item.lastName }}</td>
                <td>{{ item.email }}</td>
                <td>{{ item.role }}</td>
                
                <td>
                  <b-button v-on:click="handleEditRoleButton(item)" pill variant="warning" size="sm" >Edit Role</b-button>
                </td>
                <td>
                  <b-button v-on:click="handleDeleteUserButton(item)" pill variant="danger" size="sm" >Delete</b-button>
                </td>
              </tr>
            </tbody>
          </table>
          </div>
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
        firstNameInput:"",
        roleNameInput:"",
        lastNameInput:"",
    
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
        },
        filteredUsers:function(){
            return this.mainTable.filter(user=>user.firstName.toLowerCase().includes(this.firstNameInput.toLowerCase())&& user.role.toLowerCase().includes(this.roleNameInput.toLowerCase()) && user.lastName.toLowerCase().includes(this.lastNameInput.toLowerCase()))
        },
    handleDeleteUserButton:function(item){
  this.user = item
            if(!(this.$store.state.auth.user.email == this.user.email))
            {
                UserService.deleteUser(this.user.email).then(()=>(
             this.forceUpdate()))
    }
    },
    },
 mounted() {
        UserService.getUsers().then(array=>{this.mainTable = array})
}
}
</script>