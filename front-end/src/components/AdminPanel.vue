<template>
  <div>
    <b-navbar toggleable="sm" type="dark" variant="dark">
      <b-navbar-toggle target="nav-text-collapse"></b-navbar-toggle>

      <b-navbar-brand>Garden Shop</b-navbar-brand>

      <b-collapse id="nav-text-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-text>Admin Panel</b-nav-text>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
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
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in mainTable" v-bind:key="item.email">
                <td>{{ item.email }}</td>
                <td>{{ item.firstName }}</td>
                <td>{{ item.lastName }}</td>
                <td>
                  <b-button pill variant="success" size="sm">Edit</b-button>
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
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in mainTable" v-bind:key="item.email">
                <td>{{ item.name }}</td>
                <td>
                  <b-button pill variant="success" size="sm">Edit</b-button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "AdminPanel",
  data() {
    return {
      changeMainTable: "empty",
      items: [{ option: "Show users" }, { option: "Show roles" }],
      mainTable: [],
      mainTableHeaders: ["email", "firstName", "lastName"],
      users: [
        { email: "1234@wp.pl", firstName: "Alicja", lastName: "Pokrzywnicka" },
        { email: "1212@wp.pl", firstName: "Antoni", lastName: "Ulicki" },
        { email: "1313@wp.pl", firstName: "Kamil", lastName: "Bednarz" },
        { email: "1414@wp.pl", firstName: "Mateusz", lastName: "Kiszonka" }
      ],
      roles: [
        { name: "USER", desc: "Read permission" },
        { name: "ADMIN", desc: "Full power" }
      ]
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
      var token =
        "eyJhbGciOiJSUzUxMiJ9.eyJyb2xlIjoiUkVBRF9QUklWSUxFR0UiLCJuYW1lIjoiQWRhbSBHbmlhZHppayIsInN1YiI6InVzZXJAdnAucGwiLCJpYXQiOjE1NzY0MjkwNTEsImV4cCI6MTU3OTAyMTA1MX0.py1Xhjs_-mYdSCdH3nYxYJ5jl-4OFhJEm1lAVGEBSH8jwZ7haERgA1Vb2oUvh2r4OtKy3cw_T7uusEmFqxVZJhe9lLeMp1YWDgvuXSbdxw9vLh7sZCTM_P4iDGEqoRQd0M76HPtQln8v7zAKTDXGRYc3WAnT2VzabvNiKEL1i3rWekRJZ2x1htMAgjPTVVDgn9fYbfyyrfzy_hC8sQnFrCMk-nsmJU6QWFX2b69IX7B26GsJSf7xN48hJfT2hp8kYqbOWAbSfuLUQAkX6jb8HJyggyqWw4gX-m6vGPBhalCVpVsp6X7hrcna7HFUR1U81W1Dv-4BJ_U9RARLvwokPg";
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
    getRolesResponseData: function() {
      var root = "http://localhost:8080";
      var token =
        "eyJhbGciOiJSUzUxMiJ9.eyJyb2xlIjoiUkVBRF9QUklWSUxFR0UiLCJuYW1lIjoiQWRhbSBHbmlhZHppayIsInN1YiI6InVzZXJAdnAucGwiLCJpYXQiOjE1NzY0MjkwNTEsImV4cCI6MTU3OTAyMTA1MX0.py1Xhjs_-mYdSCdH3nYxYJ5jl-4OFhJEm1lAVGEBSH8jwZ7haERgA1Vb2oUvh2r4OtKy3cw_T7uusEmFqxVZJhe9lLeMp1YWDgvuXSbdxw9vLh7sZCTM_P4iDGEqoRQd0M76HPtQln8v7zAKTDXGRYc3WAnT2VzabvNiKEL1i3rWekRJZ2x1htMAgjPTVVDgn9fYbfyyrfzy_hC8sQnFrCMk-nsmJU6QWFX2b69IX7B26GsJSf7xN48hJfT2hp8kYqbOWAbSfuLUQAkX6jb8HJyggyqWw4gX-m6vGPBhalCVpVsp6X7hrcna7HFUR1U81W1Dv-4BJ_U9RARLvwokPg";
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
    }
  }
};
</script>
