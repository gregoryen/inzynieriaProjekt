 <template>
 
 <div>
    <tr>  <AdminPanelEditRole :role="role" :closeMethod="handleClose"  :key="EditRoleReRender" :methodForceUpdate="methodForceUpdate" v-if="this.editRole" /></tr>
 <table
            class="table b-table table-sm table-bordered">
            <thead class="thead-light">
              <tr>
                <th>Role</th>
                <th>Edit</th>
                <th>Delete</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in this.mainTable  " v-bind:key="item.name">
                <td>{{ item.name }}</td>
                <td>
                  <b-button v-on:click="handleEditRoleButton(item)" pill variant="warning" size="sm">Edit</b-button>
                </td>
                <td>
                  <b-button  pill variant="danger" size="sm">Delete</b-button>
                </td>
              </tr>
            </tbody>
          </table>
</div>
</template>
<script>


import UserService from '../services/user.service.js';
import AdminPanelEditRole from './AdminPanelEditRole';
export default {
  
  components:{
    AdminPanelEditRole
  },
  props:{
      forceUpdate:Function
  },
  name: "AdminPanelShowRoles",
  data() {
    return {
      role:null,
      editRole:false,
      mainTable: [],
      EditRoleReRender:true
    };
  },
    methods:{
       handleEditRoleButton :function(item)
        {

            this.role = item
            if(!(this.$store.state.auth.user.role == this.role[0]))
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
        UserService.getRoles().then(response=>(this.mainTable = response))
}
}
</script>