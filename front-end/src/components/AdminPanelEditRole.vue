 <template>
 <div>
 <table
            class="table b-table table-sm table-bordered">
            <thead class="thead-light">
              <tr>
                <th>Role name</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in this.privilegesArray  " v-bind:key="item.name">
                <td><input type="checkbox" :ref="item.name" :value="item.name" v-model="additional"/>{{ item.name }}</td>
              </tr>
            </tbody>
          </table>
        <button v-on:click="handleSave">Save</button>
            
</div>
</template>
<script>


import UserService from '../services/user.service.js';
export default {
  name: "AdminPanelEditRole",
  props:{
      role:Object,
      closeMethod:Function,
      methodForceUpdate:Function
  },
  data() {
    return {
      privilegesArray: [],
      additional:[]
    };
  },
    methods:{

    
    handleSave:function(){
      let id = this.role._links.self.href.substring(this.role._links.self.href.length-1,this.role._links.self.href.length)

        UserService.updateRolePrivileges(this.additional.join('-'),id)
        this.methodForceUpdate()
        this.closeMethod()
    }
    },
 mounted() {

        UserService.getPrivileges().then(response=>{
            this.privilegesArray=response
        })
}
}
</script>