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
        img-src="../../assets/tasmaKroplujaca.jpg"
      >
      </b-carousel-slide>

      <b-carousel-slide
        caption="Tree whitening"
        img-src="../../assets/bielenie.jpg"
      >
      </b-carousel-slide>
    </b-carousel>
    <div>
      <div class="space" />
      <b-card-group deck>
        <div class="small-card-deck">
        <b-card
          border-variant="success"
          img-src="../../assets/grabie.jpg"
          img-alt="Card image"
          img-top
          img-height="560px"
        >
          <h2>
            Our best rating product
          </h2>
        </b-card>
        <b-card
          border-variant="success"
          img-src="../../assets/opryskiwacze.jpg"
          img-alt="Card image"
          img-bottom
        >
          <h2>
            Now all sprayers on sale !!!
          </h2>
        </b-card>
        </div>
      </b-card-group>
    </div>
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      slide: 0,
      sliding: null
    };
  },
  methods: {
    onSlideStart() {
      this.sliding = true;
    },
    onSlideEnd() {
      this.sliding = false;
    }
  }
};
</script>
}
