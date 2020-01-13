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
                <td @click="getUsersResponseData">Show Users</td>
              </tr>
              <tr>
                <td @click="getRolesResponseData">Show Roles</td>
              </tr>
              <tr>
                <td @click="getPrivilegesResponseData">Show Privileges</td>
              </tr>
            </tbody>
          </table>
        </div>
      </b-col>
      <b-col cols="12" md="8">
        <div class="main-table">
          <table
            class="table b-table table-sm table-bordered"
            v-if="this.changeMainTable === 'users'"
          >
            <thead class="thead-light">
              <tr>
                <th>Email</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Edit</th>
                <th>Delete</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in mainTable" v-bind:key="item.email">
                <td>{{ item.email }}</td>
                <td>{{ item.firstName }}</td>
                <td>{{ item.lastName }}</td>
                <td>
                  <b-button pill variant="warning" size="sm">Edit</b-button>
                </td>
                <td>
                  <b-button pill variant="danger" size="sm">Delete</b-button>
                </td>
              </tr>
            </tbody>
          </table>
          <table
            class="table b-table table-sm table-bordered"
            v-if="this.changeMainTable === 'roles'"
          >
            <thead class="thead-light">
              <tr>
                <th>Role</th>
                <th>Edit</th>
                <th>Delete</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in mainTable" v-bind:key="item.name">
                <td>{{ item.name }}</td>
                <td>
                  <b-button pill variant="warning" size="sm">Edit</b-button>
                </td>
                <td>
                  <b-button pill variant="danger" size="sm">Delete</b-button>
                </td>
              </tr>
            </tbody>
          </table>
          <table
            class="table b-table table-sm table-bordered"
            v-if="this.changeMainTable === 'privileges'"
          >
            <thead class="thead-light">
              <tr>
                <th>Privileges</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in mainTable" v-bind:key="item.name">
                <td>{{ item.name }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </b-col>
    </b-row>
  </div>
</template>

<script>
export default {
  name: "AdminPanel",
  data() {
    return {
      changeMainTable: "empty",
      mainTable: []
    };
  },
  methods: {
    getUsersResponseData() {
      this.$store.dispatch("admin/getUsers").then(() => {
        this.setDataFromStore();
      });
    },
    getRolesResponseData() {
      this.$store.dispatch("admin/getRoles").then(() => {
        this.setDataFromStore();
      });
    },
    getPrivilegesResponseData() {
      this.$store.dispatch("admin/getPrivileges").then(() => {
        this.setDataFromStore();
      });
    },
    setDataFromStore() {
      this.mainTable = this.$store.state.admin.mainTable;
      this.changeMainTable = this.$store.state.admin.changeMainTable;
    }
  }
};
</script>
