<template>
  <div class="container">
    <h1>Notice List</h1>
    <div class="btn-group pull-right" style="font-size: 14px; line-height: 2;margin-bottom: 10px">
      <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">글쓰기</button>

      <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <form @submit.prevent="onSave(title, content)">
              <div class="modal-header" style="width:100%">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">공지사항 추가</h4>
              </div>
              <div class="modal-body" style="width:100%">
                <div class="input-group" style="width:95%;">
                  <div style="display:flex;flex-direction:row;">
                    <span style="width:80px;padding-right:10px">title</span>
                    <input type="text" class="form-control" v-model="title" placeholder="제목을 입력하세요" required />
                  </div>
                  <div style="display:flex;flex-direction:row;margin-top:5px">
                    <span style="width:80px;padding-right:10px">content</span>
                    <textarea type="text" class="form-control" v-model="content" placeholder="내용을 입력하세요" required style="height:300px"/>
                  </div>
                </div>
              </div>
              <div class="modal-footer" style="width:100%">
                <button ref='myCloseBtn' type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary">Save changes</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
    <paginated-list :list-array="articles" @event="refresh" />
  </div>
</template>

<script>
import PaginatedList from './PaginatedList'

export default {
  name: 'notice-list',
  components: {
    PaginatedList
  },
  data () {
    return {
      title: '',
      content: '',
      articles: []
    }
  },
  created () {
    this.$Axios.get('/api/user')
      .then(response => {
        console.log(response)
        if (!response.data.userId) {
          this.$store.dispatch('LOGOUT').then(() => window.location.replace('/'))
        }
        const accessToken = sessionStorage.getItem('accessToken')
        this.$Axios.defaults.headers.common['Authorization'] = `${accessToken}`
        this.refresh()
      })
      .catch(err => {
        console.log(err)
        this.$store.dispatch('LOGOUT').then(() => window.location.replace('/'))
      })
  },
  methods: {
    onSave (title, content) {
      const payload = {
        'author': sessionStorage.getItem('userName'),
        'content': content,
        'title': title
      }
      this.$Axios.post('/api/article', payload)
        .then(response => {
          console.log(response)
          this.refresh()
        })
        .catch(err => {
          console.log(err)
        })
      this.$refs.myCloseBtn.click()
      this.title = ''
      this.content = ''
    },
    refresh () {
      this.$Axios.get('/api/article')
        .then(response => {
          console.log(response)
          this.articles = response.data
        })
        .catch(err => {
          console.log(err)
        })
    }
  }
}
</script>

<style>
h1 {
  color: #454545;
  text-align: center;
}
</style>
