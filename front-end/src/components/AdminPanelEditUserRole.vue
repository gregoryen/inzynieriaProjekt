 <template>
 <div>
 <table
            class="table b-table table-sm table-bordered">
            <thead class="thead-light">
              <tr>
                <th>Privilege</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in this.roleArray  " v-bind:key="item.name">
                <td><input type="checkbox" :ref="item.name" :value="item.name" v-model="additional" v-on:click="handleChangeChecked"/>{{ item.name }}</td>
              </tr>
            </tbody>
          </table>
        <button v-on:click="handleSave">Save</button>
            
</div>
</template>
<script>


import UserService from '../services/user.service.js';
export default {
  name: "AdminPanelEditUserRole",
  props:{
      user:Object,
      closeMethod:Function,
      methodForceUpdate:Function
  },
  data() {
    return {
      roleArray: [],
      additional:[]
    };
  },
    methods:{
handleChangeChecked :function() {
    
   this.additional=[];
},
    
    handleSave:function(){
  
        UserService.updateUserRole(this.additional[0],this.user.id)
        this.methodForceUpdate()
        this.closeMethod()
    }
    },
 mounted() {

        UserService.getRoles().then(array=>{this.roleArray = array})
}
}
</script>