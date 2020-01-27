 <template>
 <table
            class="table b-table table-sm table-bordered">
            <thead class="thead-light">
              <tr>
                <th>Privilege</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in this.mainTable  " v-bind:key="item.name">
                <td>{{ item.name }}</td>
              </tr>
            </tbody>
          </table>
</template>
<script>


import UserService from '../services/user.service.js';
export default {
  name: "AdminPanelShowPrivileges",
  data() {
    return {
      mainTable: [],
    };
  },
    
 mounted() {
        UserService.getPrivileges().then(response=>(this.mainTable = response))
}
}
</script>