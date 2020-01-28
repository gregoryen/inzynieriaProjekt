<template>
  <div>
    <b-carousel
      id="carousel-1"
      v-model="slide"
      :interval="4000"
      controls
      indicators
      background="#ababab"
      img-width="1024"
      img-height="480"
      style="text-shadow: 1px 1px 2px #333;"
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd"
    >
      <b-carousel-slide
        caption="Drip tape"
        img-src="../assets/tasmaKroplujaca.jpg"
      >
      </b-carousel-slide>
      <b-carousel-slide
        caption="Three whitening"
        img-src="../assets/bielenie.jpg"
      >
      </b-carousel-slide>
    </b-carousel>
    <table>
       
            <div v-for="item in filteredAdv()" v-bind:key="item.name">
                
              <tr class="header">{{ item.name }}</tr>
              <td class="description">{{ item.description }}</td>
              <tr class="image"><img :src="'http://localhost:8100/advImages/downloadMainImage?idAdv=' 
                    + item.id"/></tr>

            </div>
    </table>
  </div>
</template>

<script>
import UserService from '../services/user.service';
export default {
  name: "Home",
  data() {
    return {
      slide: 0,
      mainTable:null,
      show:false,
      sliding: null
    };
  },
  mounted() {
        UserService.getAdvertisement().then(array=>{this.mainTable = array.data})
},
  methods: {
    filteredAdv:function(){
if(this.mainTable.length<=5)
return this.mainTable
else
return this.mainTable.slice(0,5)


    },

    
      
    
    onSlideStart() {
      this.sliding = true;
    },
    onSlideEnd() {
      this.sliding = false;
    }
  }
};
</script>
<style>
.header{
  font-size:40px;
  font-style:italic;
}

</style>

