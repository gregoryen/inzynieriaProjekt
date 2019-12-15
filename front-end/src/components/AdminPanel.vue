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
                <td @click="getUsersTable">Show Users</td>
              </tr>
              <tr>
                <td @click="getRolesTable">Show Roles</td>
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
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in mainTable" v-bind:key="item.email">
                <td>{{ item.email }}</td>
                <td>{{ item.firstName }}</td>
                <td>{{ item.lastName }}</td>
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
                <th>Description</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in mainTable" v-bind:key="item.email">
                <td>{{ item.role }}</td>
                <td>{{ item.desc }}</td>
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
        { role: "USER", desc: "Read permission" },
        { role: "ADMIN", desc: "Full power" }
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
        "eyJhbGciOiJSUzUxMiJ9.eyJyb2xlIjoiVVNFUiIsIm5hbWUiOiJQYXVsYSBaYWNoYWN6Iiwic3ViIjoiMTIzNEB3cC5wbCIsImlhdCI6MTU3NjE3MDYzOCwiZXhwIjoxNTc4NzYyNjM4fQ.vI59RnslfI0IuvKqITGmK6oNUCkbs2yRsSWlpLrfoTgZx4i-RJ7IjzZFigqfTP_B9QIUcMRqnXPZ0VK2aREaW5dsaK4TrMWCHP8HCVbXXWadxCTH1vi8H5gjAx_gzoWiGcSerP9su8OwHb9Fs8ZvZ3nvgSQbdGBh3JsZk-EF8BIUQ3ABjLTuFRHLVqlKptZIHtu7Ala3Gid8ZleEypaXpp0xpE5h84GnWN4bX1lQLDBPUvSxYyplOtANlzW3dWvOhuqiMf6iddqlkUtmuwxMZOrX6MxyYZUP4JVdKvLfyElIdFTDw2O9yCO6K57GyN-vQDCaLw806shcAw_W9R8j6g";
      axios
        .get(root + "/users", {
          headers: {
            Authorization: "Bearer " + token,
            "Content-type": "application/json"
          }
        })
        .then(response => {
          /* eslint-disable no-console */
          console.log(response);
        })
        .catch(error => {
          /* eslint-disable no-console */
          console.log(error);
        });
    }
  }
};
</script>
