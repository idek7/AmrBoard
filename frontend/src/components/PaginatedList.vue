<template>
  <div>
    <table>
      <tr>
        <th>TITLE</th>
        <th>AUTHOR</th>
        <th>DATE</th>
        <th></th>
      </tr>
      <tr v-for="p in paginatedData" :key="p.id">
        <td style="width:55%;text-align:left">
          <div class="btn-group" style="font-size: 12px; line-height: 1;">
            <button type="button"
              class="btn-link dropdown-toggle"
              data-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
              style="text-align: left; line-height: 1.4;">
              {{ p.title }}<span class="caret"></span>
            </button>
            <ul class="dropdown-menu">
              <li style="display:flex;flex-direction:column;padding: 15px">
                <span>내용</span>
                <div style="width:500px; max-height:300px;margin-top:10px">
                  <pre style="max-height:300px">{{ p.content }}</pre>
                </div>
                <div style="margin-top:10px">최초 작성일 : {{ p.createDate | moment }}</div>
              </li>
            </ul>
          </div>
        </td>
        <td style="width:10%">{{ p.author }}</td>
        <td style="width:20%">{{ p.modDate | moment }}</td>
        <td style="width:15%">
          <div class="btn-group pull-right" style="font-size: 12px; line-height: 1;">
            <button type="button" class="btn btn-default" data-toggle="modal" data-target="#editModal" @click="onEdit(p)">수정</button>
            <button type="button" class="btn btn-default" @click="onDelete(p.id)">삭제</button>
          </div>
        </td>
      </tr>
    </table>
    <div class="btn-cover">
      <button :disabled="pageNum === 0" @click="prevPage" class="btn btn-default">
        이전
      </button>
      <span class="page-count">{{ pageNum + 1 }} / {{ pageCount }} 페이지</span>
      <button :disabled="pageNum >= pageCount - 1" @click="nextPage" class="btn btn-default">
        다음
      </button>
    </div>

    <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <form @submit.prevent="onSave(title, content)">
            <div class="modal-header" style="width:100%">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title" id="myModalLabel">공지사항 수정</h4>
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
              <button ref='editCloseBtn' type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="submit" class="btn btn-primary">Save changes</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import moment from 'moment'

export default {
  name: 'paginated-list',
  data () {
    return {
      id: '',
      title: '',
      content: '',
      pageNum: 0
    }
  },
  props: {
    listArray: {
      type: Array,
      required: true
    },
    pageSize: {
      type: Number,
      required: false,
      default: 10
    }
  },
  methods: {
    nextPage () {
      this.pageNum += 1
    },
    prevPage () {
      this.pageNum -= 1
    },
    onEdit (article) {
      console.log(article)
      this.id = article.id
      this.title = article.title
      this.content = article.content
    },
    onDelete (id) {
      if (confirm('Do you really want to delete?')) {
        this.$Axios.delete('/api/article/' + id)
          .then(response => {
            console.log(response)
            this.$emit('event')
          })
          .catch(err => {
            console.log(err)
          })
      }
    },
    onSave (title, content) {
      const payload = {
        'id': this.id,
        'content': content,
        'title': title
      }
      this.$Axios.put('/api/article', payload)
        .then(response => {
          console.log(response)
          this.$emit('event')
        })
        .catch(err => {
          console.log(err)
        })
      console.log(this.$refs, payload.id)
      this.$refs.editCloseBtn.click()
      this.title = ''
      this.content = ''
    }
  },
  filters: {
    moment: function (date) {
      return moment(date).format('YYYY-MM-DD HH:mm:ss')
    }
  },
  computed: {
    pageCount () {
      let listLeng = this.listArray.length
      let listSize = this.pageSize
      let page = Math.floor(listLeng / listSize)
      if (listLeng % listSize > 0) page += 1
      return page
    },
    paginatedData () {
      const start = this.pageNum * this.pageSize
      const end = start + this.pageSize
      return this.listArray.slice(start, end)
    }
  }
}
</script>

<style>
table {
  width: 100%;
  border-collapse: collapse;
}
table th {
  text-align: center;
  font-size: 1.2rem;
  padding: 0.5rem 0;
}
table tr {
  height: 2rem;
  text-align: center;
  border-bottom: 1px solid #505050;
}
table tr:first-of-type {
  border-top: 2px solid #404040;
}
table tr td {
  padding: 0.5rem 0;
  font-size: 1.3rem;
}
.btn-cover {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 1.5rem;
  text-align: center;
}
.btn-cover .page-btn {
  width: 5rem;
  height: 2rem;
  letter-spacing: 0.5px;
}
.btn-cover .page-count {
  padding: 0 1rem;
}
</style>
