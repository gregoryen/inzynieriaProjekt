<template>
    <dropdown>
      <template slot="btn">{{root.}}</template>
      <template slot="body">
        <ul>
          <li>Custom item</li>
          <li>
            <dropdown :trigger="'hover'" :role="'sublist'" :align="'right'">
              <template slot="btn">Sublist right</template>
              <template slot="body">
                Sublist content
                <ul>
                  <li>Custom subitem</li>
                  <dropdown :trigger="'hover'" :role="'sublist'" :align="'right'">
                    <template slot="btn">Sublist right</template>
                    <template slot="body">Sublist content</template>
                  </dropdown>
                </ul>
              </template>
            </dropdown>
          </li>
          <li>Custom item</li>
          <li>
            <dropdown :trigger="'hover'" :role="'sublist'" :align="'left'">
              <template slot="btn">Sublist left</template>
              <template slot="body">Sublist content</template>
            </dropdown>
          </li>
        </ul>
      </template>
    </dropdown>
</template>

<script>
import Dropdown from "bp-vuejs-dropdown";
export default {
  components: { Dropdown },
  name: "Category",
  props: {
    root: Object
  }
};
</script>
