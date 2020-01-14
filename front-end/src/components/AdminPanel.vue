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
              <tr>
                <td @click="showOpinionsTable">Show Opinions</td>
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
      <OpinionsInAdminPanel  v-if="this.changeMainTable === 'opinions'"/>
    </b-row>
  </div>
</template>

<script>
import axios from "axios";
import OpinionsInAdminPanel from "./OpinionsInAdminPanel";
export default {
  name: "AdminPanel",
  components: {
    OpinionsInAdminPanel
  },
  data() {
    return {
      changeMainTable: "empty",
      items: [{ option: "Show users" }, { option: "Show roles" }],
      mainTable: [],
    };
  },
  methods: {
    getUsersTable() {
      /* eslint-disable no-console */
      console.log("users");
      this.mainTable = this.users;
      this.changeMainTable = "users";
    },
    getRolesTable() {
      /* eslint-disable no-console */
      console.log("roles");
      this.mainTable = this.roles;
      this.changeMainTable = "roles";
    },
    getUsersResponseData: function() {
      var root = "http://localhost:8080";
      var token = this.$store.state.auth.user.jwtToken
       const config = {
        headers: {
          Authorization: "Bearer " + token,
          "Content-type": "application/json"
        }
      };
      axios
        .get(root + "/users", config)
        .then(response => {
          /* eslint-disable no-console */
          console.log(response.data._embedded.users);
          this.mainTable = response.data._embedded.users;
          this.changeMainTable = "users";
        })
        .catch(error => {
          /* eslint-disable no-console */
          console.log(error);
        });
    },
    showOpinionsTable: function ()  {
      this.changeMainTable = "opinions";
    },
    getRolesResponseData: function() {
      var root = "http://localhost:8080";
      var token = this.$store.state.auth.user.jwtToken
           const config = {
        headers: {
          Authorization: "Bearer " + token,
          "Content-type": "application/json"
        }
      };
      axios
        .get(root + "/roles", config)
        .then(response => {
          /* eslint-disable no-console */
          console.log(response.data._embedded.roles);
          this.mainTable = response.data._embedded.roles;
          this.changeMainTable = "roles";
        })
        .catch(error => {
          /* eslint-disable no-console */
          console.log(error);
        });
    },
        getPrivilegesResponseData: function() {
      var root = "http://localhost:8080";
      var token = this.$store.state.auth.user.jwtToken
            const config = {
        headers: {
          Authorization: "Bearer " + token,
          "Content-type": "application/json"
        }
      };
      axios
        .get(root + "/privileges", config)
        .then(response => {
          /* eslint-disable no-console */
          console.log(response.data._embedded.privileges);
          this.mainTable = response.data._embedded.privileges;
          this.changeMainTable = "privileges";
        })
        .catch(error => {
          /* eslint-disable no-console */
          console.log(error);
        });
    }
  }
};
</script>
